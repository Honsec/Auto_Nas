package genius.baselib.frame.base;

import com.sera.hongsec.volleyhelper.MultiRequest;

/**
 * Created by Hongsec on 2016-09-05.
 */
public abstract class BaseMultiApi<T> extends MultiRequest<T> {

    @Override
    public String baseUrl() {

        return "";
    }

    @Override
    public void ErrorStatusProcess(int statusCode) {

        switch (statusCode){

            case 401:
                //TODO logout
                break;
            case 409:
                //TODO error
                break;

        };


    }
}
