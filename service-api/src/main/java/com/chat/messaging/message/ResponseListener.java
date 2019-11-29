package com.chat.messaging.message;

import com.chat.messaging.dto.ErrorMessageDto;

/**
 *
 * @author gdimitrova
 */
public interface ResponseListener<T> {

    public void onSuccess(T response);

    public void onError(ErrorMessageDto error);

}
