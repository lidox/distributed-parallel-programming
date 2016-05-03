package lsg;

//Aufgabe 1

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConcurrentBitVectorLock implements FrontierList
{
	private long[] m_vector = null;

	private long m_itPos = 0;
	private long m_count = 0;

	private Lock reentrantLock = new ReentrantLock(false);

	/**
	 * Constructor
	 * @param p_maxElementCount Specify the maximum number of elements.
	 */
	public ConcurrentBitVectorLock(final long p_maxElementCount)
	{
		m_vector = new long[((int) ((p_maxElementCount / 64L) + 1L))];
	}

	@Override
	public void set(final long p_index)
	{
		long tmp = (1L << (p_index % 64L));
		int index = (int) (p_index / 64L);

		reentrantLock.lock();
		try{
			long val = m_vector[index];

			if ((val & tmp) == 0)
			{
				m_vector[index] = (val | tmp);
				m_count++;
			}
		} finally{
			reentrantLock.unlock();
		}

	}

	@Override
	public boolean contains(long p_val)
	{
		long tmp = (1L << (p_val % 64L));
		int index = (int) (p_val / 64L);
		reentrantLock.lock();
		boolean ret = ((m_vector[index] & tmp) != 0);
		reentrantLock.unlock();
		return ret;
	}

	@Override
	public long size()
	{
		return m_count;
	}

	@Override
	public boolean isEmpty()
	{
		return m_count == 0;
	}

	@Override
	public void reset()
	{
		reentrantLock.lock();
		m_itPos = 0;
		m_count = 0;
		for (int i = 0; i < m_vector.length; i++) {
			m_vector[i] = (long)0;
		}
		reentrantLock.unlock();
	}

	@Override
	public long popFront()
	{
		reentrantLock.lock();

		// this section keeps threads out
		// if the vector is already empty
		long count = m_count;
		if (count > 0) {
			m_count = count - 1;

		} else {
			reentrantLock.unlock();
			return -1;
		}

		while (true)
		{
			long itPos = m_itPos;

			if ((m_vector[(int) (itPos / 64L)] & (1L << itPos % 64L)) != 0)
			{
				m_itPos = itPos + 1;
				reentrantLock.unlock();
				return itPos;
			}

			m_itPos = itPos + 1;

		}

	}

}
