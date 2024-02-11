package com.hb.messenger.exceptions;


import lombok.Getter;

@Getter
public class MessengerException extends RuntimeException{
   private final ErrorCode errorCode;

   private MessengerException(ErrorCode errorCode){
       super(errorCode.getMessage());
       this.errorCode=errorCode;
   }

   public static MessengerException error(ErrorCode errorCode){
        return new MessengerException(errorCode);
    }


}
