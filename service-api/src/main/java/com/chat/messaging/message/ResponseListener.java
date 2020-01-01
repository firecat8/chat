package com.chat.messaging.message;

import com.chat.messaging.vo.ErrorVo;

/**
 *
 * @author gdimitrova
 */
public interface ResponseListener<T> {

    public void onSuccess(T response);

    public void onError(ErrorVo error);

}
