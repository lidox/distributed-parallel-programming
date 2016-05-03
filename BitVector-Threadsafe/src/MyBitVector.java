
import java.util.concurrent.locks.ReentrantLock;

import lsg.FrontierList;

/**
 * Concurrent BitVector Lock
 * @author schaefer
 *
 */
public class MyBitVector implements FrontierList {

	// 1000 * 8 = 8k nodes (2^31 slots)
	//private Byte[] byteArray = new Byte[1000];
	
	// 
	private int vertorSize;
	private ReentrantLock lock = new ReentrantLock();
	private long[] vector;
	
	/** volatile wegen Visibility-Gründen deklariert. alternativ kann man lock() und unlick nutzen. 
	 * Ohne volatile wird der Cache nicht geflush und es kann zu falschen ausgabewerten kommen 
	 * Bei der Programmiersprache C hingegen muss 'volatile' angegeben werden, damit der Compiler die Variable
	 * nicht 'weg-optimiert'
	 */
	private volatile long vectorElementsCount = 0; 
	private volatile long popFrontCount = 0;
	
	/**
	 * Setzt das Bit an der Stelle p_val und markiert somit den Knoten mit dem Index p_val
	 */
	@Override
	public void set(long p_val) {
		if ( vector.length >= p_val) {
			lock.lock();
			try{
				vector[(int) p_val] = p_val;
				this.vectorElementsCount += 1;
			} finally{
				lock.unlock();
			}	
		}
	}

	/**
	 * Prueft ob das Bit an der Stelle p_val gesetzt ist und somit ob der Knoten mit dem Index p_val markiert ist
	 */
	@Override
	public boolean contains(long p_val) {
		if ( vector.length >= p_val) {
			lock.lock();
			try{
				if (vector[(int) p_val] == 0) {
					return false;
				}
			} finally{
				lock.unlock();
			}
		}
		return true;
	}
	
	/**
	 * Gibt die Anzahl der gesetzten Bits bzw. markierten Knoten zurück
	 */
	@Override
	public long size() {
		return this.vectorElementsCount;
	}

	/**
	 * Prueft ob der Vektor leer ist bzw. kein Knoten markiert ist
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return (this.vectorElementsCount == 0) ? true : false;
	}

	/**
	 * Löscht alle Bits (setzen auf 0) und setzt auch jegliche Variablen, die für popFront benötigt werden zurück
	 */
	@Override
	public void reset() {
		this.vector = new long[vertorSize];
	}

	/**
	 * Gibt von der aktuell gespeicherten Position (Index 0 beim ersten Aufruf) aus die Stelle im Vektor zurück,
	 * wo das nächste Bit gesetzt ist und speichert die aktuelle Position der Iteration für weitere Aufrufe. Gibt -1 zurück 
	 * wenn das Ende des Vektors erreicht wurde.
	 * @return
	 */
	@Override
	public long popFront() {
		while (this.popFrontCount <= vector.length) {
			
			if (vector[(int) popFrontCount] != 0) {
				
				// critical code
				lock.lock();
				try{
					vector[(int) popFrontCount] = 0;
				} finally{
					lock.unlock();
				}
				
				return popFrontCount;
			}
			
			lock.lock();
			try{
				this.popFrontCount += 1;
			} finally{
				lock.unlock();
			}
			
		}
		return -1;
	}

	public MyBitVector(int vectorSlotSize) {
		this.vertorSize = vectorSlotSize;
		this.vector = new long[vertorSize];
	}
}
