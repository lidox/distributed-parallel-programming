//Aufgabe 2

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;


public class ConcurrentBitVector implements FrontierList
{
	private AtomicLongArray vector = null;
	private AtomicLong position = new AtomicLong(0);
	private AtomicLong count = new AtomicLong(0);

	/**
	 * Constructor
	 * @param verctorSize Specify the maximum number of elements
	 */
	public ConcurrentBitVector(final long verctorSize)
	{
		vector = new AtomicLongArray((int) ((verctorSize / 64L) + 1L));
	}

	@Override
	public void set(final long index)
	{
		long tmp = (1L << (index % 64L));
		int index = (int) (index / 64L);

		while (true)
		{
			long val = vector.get(index);
			if ((val & tmp) == 0)
			{
				if (!vector.compareAndSet(index, val, val | tmp))
					continue;
				count.incrementAndGet();
			}

			break;
		}
	}

	@Override
	public boolean contains(long p_val) {
		long tmp = (1L << (p_val % 64L));
		int index = (int) (p_val / 64L);
		return ((vector.get(index) & tmp) != 0);
	}

	@Override
	public long size()
	{
		return count.get();
	}

	@Override
	public boolean isEmpty()
	{
		return count.get() == 0;
	}

	@Override
	public void reset()
	{
		position.set(0);
		count.set(0);
		for (int i = 0; i < vector.length(); i++) {
			vector.set(i, 0);
		}
	}

	@Override
	public long popFront()
	{
		while (true)
		{
			// this section keeps threads out
			// if the vector is already empty
			long count = count.get();
			if (count > 0) {
				if (!count.compareAndSet(count, count - 1)) {
					continue;
				}
			} else {
				return -1;
			}

			while (true)
			{
				long itPos = position.get();

				if ((vector.get((int) (itPos / 64L)) & (1L << itPos % 64L)) != 0)
				{
					if (!position.compareAndSet(itPos, itPos + 1)) {
						continue;
					}

					return itPos;
				}

				if (!position.compareAndSet(itPos, itPos + 1)) {
					continue;
				}
			}
		}
	}

}
