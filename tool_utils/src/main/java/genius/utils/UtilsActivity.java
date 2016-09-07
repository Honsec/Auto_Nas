package genius.utils;

import android.app.Activity;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Activity 管理table
 */
public class UtilsActivity {

	private static Hashtable<String, Stack<Activity>> activityMap;

	private static UtilsActivity activityManagerUtils;
	
	private UtilsActivity(){
		
	}
	
	public static UtilsActivity getInstance(){
		if(null == activityManagerUtils){
			activityManagerUtils = new UtilsActivity();
		}

		if (activityMap == null) {
			activityMap = new Hashtable<String, Stack<Activity>>();
		}

		return activityManagerUtils;
	}


	public void registerActivity(Activity context,String name) {
		try {
			Stack<Activity> stack = activityMap.get(name);
			if (stack == null) {
                stack = new Stack<Activity>();
            }

			stack.push(context);
			activityMap.put(name, stack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unregisterActivity(Activity context,String name) {
		try {
			Stack<Activity> stack = activityMap.get(name);
			if (stack == null) {
                return;
            }

			for (Activity activity : stack) {
                if (context.equals(activity)) {
                    stack.remove(activity);

                    if (stack.size() == 0) {
                        activityMap.remove(context.getClass().getName());
                    }

                    break;
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Stack<Activity> findActivities(String name) {
		return activityMap.get(name);
	}

	public static Activity findActivity(Class<?> cls) {
		final Stack<Activity> stack = activityMap.get(cls.getName());
		if (stack != null) {
			for (Activity activity : stack) {
				if (activity.getClass().equals(cls)) {
					return activity;
				}
			}
		}

		return null;
	}


	public void killAllActivities() {
		try {
			Stack<Activity> stack;
			for (String name : activityMap.keySet()) {
                stack = activityMap.get(name);
                if (stack != null) {
                    for (Activity activity : stack) {
                        activity.finish();
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void killAllActivities(Class<?> cls) {

		try {
			Stack<Activity> stack;
			for (String name : activityMap.keySet()) {
                stack = activityMap.get(name);
                if (stack != null) {
                    for (Activity activity : stack) {
                        if (!activity.getClass().equals(cls)) {
                            activity.finish();
                        }

                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
