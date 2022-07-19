package org.lilac.srtp.common;

import javax.xml.transform.Result;

public class MessageBox<T> {
    private String code; //0为成功的标识码，其他错误码自定义
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageBox{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public MessageBox() {
    }

    public MessageBox(T data) {
        this.data = data;
    }

    public static MessageBox success(){
        MessageBox messageBox = new MessageBox<>();
        messageBox.setCode("200");
        messageBox.setMessage("success");
        return messageBox;
    }

    public static <T> MessageBox<T> success(T data){
        MessageBox messageBox = new MessageBox<>(data);
        messageBox.setCode("200");
        messageBox.setMessage("success");
        return messageBox;
    }

    public static MessageBox error(String code, String message){
        MessageBox messageBox = new MessageBox();
        messageBox.setCode(code);
        messageBox.setMessage(message);
        return  messageBox;
    }

}
