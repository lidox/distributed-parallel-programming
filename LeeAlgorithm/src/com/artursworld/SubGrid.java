package com.artursworld;

import java.util.concurrent.locks.StampedLock;

/**
 * Subgrid beinhaltet Sperrmechanismus 
 */
public class SubGrid {

    private StampedLock lock;

    public SubGrid(){
        this.lock = new StampedLock();
    }

    public long writeLock(){
        return lock.writeLock();
    }

    public void unlock(long stamp){
        lock.unlock(stamp);
    }

}
