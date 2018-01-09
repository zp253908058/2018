package com.teeny.wms.app;

import android.app.Activity;

import java.util.Stack;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AppManager
 * @since 2017/7/8
 */

public abstract class AppManager {

    private static volatile AppManager mInstance;

    public static AppManager getInstance() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                if (mInstance == null) {
                    mInstance = new AppManagerImpl();
                }
            }
        }
        return mInstance;
    }

    /**
     * Pushes an Activity into the top of this manager.
     *
     * @param target the activity to be pushed onto this manager.
     * @param <A>    type of the target.
     * @return activity argument.
     */
    public abstract <A extends Activity> A push(A target);

    /**
     * Removes the activity at the top of this manager and returns that
     * activity as the value of this function.
     * If the manager does not contain the activity, it is unchanged.
     *
     * @return the activity at the top of this manager.
     */
    public abstract Activity pop();

    /**
     * Looks at the activity at the top of this manager without removing it
     * from the stack.
     * Return null if the manager does not contain the activity.
     *
     * @return The activity at the top of this manager.
     */
    public abstract Activity peek();

    /**
     * Removes the first occurrence of the specified activity in this manager
     * If the manager does not contain the activity, it is unchanged.
     *
     * @param target the activity to be removed from this manager, if present
     * @param <A>    type of the target.
     * @return true if the manager contained the specified activity.
     */
    public abstract <A extends Activity> boolean remove(A target);

    /**
     * Remove and finish the first occurrence of the specified activity in this manager
     * If the manager does not contain the activity, it is unchanged.
     *
     * @param target the activity to be finished in this manager.
     * @param <A>    type of the target
     * @return true if the manager contained the specified activity.
     */
    public abstract <A extends Activity> boolean finish(A target);

    /**
     * Return the count of the specified activity instance in this manager.
     *
     * @param target the class type of the target.
     * @param <A>    type of the target.
     * @return the count of the specified type of the activity.
     */
    public abstract <A extends Activity> int count(Class<A> target);

    /**
     * Return the number of activity in this manager.
     *
     * @return the number of activity in this manager.
     */
    public abstract int size();

    /**
     * Tests if this manager is empty.
     *
     * @return true if and only if this manager contains no items, false otherwise.
     */
    public abstract boolean isEmpty();

    /**
     * Remove and finish all activity in this manager, if the manager is empty, it is unchanged.
     */
    public abstract void exit();

    private static class AppManagerImpl extends AppManager {

        private Stack<Activity> mManager;

        AppManagerImpl() {
            mManager = new Stack<>();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <A extends Activity> A push(A target) {
            return target == null ? null : (A) mManager.push(target);
        }

        @Override
        public Activity pop() {
            return mManager.empty() ? null : mManager.pop();
        }

        @Override
        public Activity peek() {
            return mManager.empty() ? null : mManager.peek();
        }

        @Override
        public <A extends Activity> boolean remove(A target) {
            return !mManager.empty() && mManager.remove(target);
        }

        @Override
        public <A extends Activity> boolean finish(A target) {
            int index = mManager.search(target);
            if (index >= 0) {
                mManager.remove(index).finish();
                return true;
            }
            return false;
        }

        @Override
        public <A extends Activity> int count(Class<A> target) {
            int count = 0;
            for (Activity activity : mManager) {
                count += activity.getClass().equals(target) ? 1 : 0;
            }
            return count;
        }

        @Override
        public int size() {
            return mManager.size();
        }

        @Override
        public boolean isEmpty() {
            return mManager.empty();
        }

        @Override
        public void exit() {
            while (!mManager.isEmpty()) {
                mManager.pop().finish();
            }
        }
    }
}
