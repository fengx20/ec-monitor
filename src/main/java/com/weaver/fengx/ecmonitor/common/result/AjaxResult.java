package com.weaver.fengx.ecmonitor.common.result;

import java.io.Serializable;

/**
 * @author Fengx
 * 全局返回结果类
 **/
public class AjaxResult<T> implements Serializable {

    /**
     * 1、这句话的意思是定义程序序列化ID
     *
     * 2、什么是序列化？
     * Serializable，Java的一个接口，用来完成java的序列化和反序列化操作的；
     * 任何类型只要实现了Serializable接口，就可以被保存到文件中，或者作为数据流通过网络发送到别的地方。也可以用管道来传输到系统的其他程序中；
     * java序列化是指把java对象转换为字节序列的过程，而java反序列化是指把字节序列恢复为java对象的过程
     *
     * 3、序列化id (serialVersionUID)
     * 序列化ID，相当于身份认证，主要用于程序的版本控制，保持不同版本的兼容性，在程序版本升级时避免程序报出版本不一致的错误。
     * 如果定义了private static final long serialVersionUID = 1L，那么如果你忘记修改这个信息，
     * 而且你对这个类进行修改的话，这个类也能被进行反序列化，而且不会报错。一个简单的概括就是，如果你忘记修改，那么它是会版本向上兼容的。
     * 如果没有定义一个名为serialVersionUID，类型为long的变量，Java序列化机制会根据编译的class自动生成一个serialVersionUID，
     * 即隐式声明。这种情况下，只有同一次编译生成的class才会生成相同的serialVersionUID 。
     * 此时如果对某个类进行修改的话，那么版本上面是不兼容的，就会出现反序列化报错的情况。
     *
     * 4、在实际的开发中，重新编译会影响项目进度部署，所以我们为了提高开发效率，不希望通过编译来强制划分软件版本，
     * 就需要显式地定义一个名为serialVersionUID，类型为long的变量，不修改这个变量值的序列化实体都可以相互进行串行化和反串行化。
     */
    private static final long serialVersionUID = 6060726152164799117L;

    private int code;

    private String message;

    private T data;

    public AjaxResult() {}
    public AjaxResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功-无数据返回
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> success() {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功-无数据返回，自定义信息
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> success(String message) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 成功-有数据返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> success(T data) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.SUCCESS.getCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败-指定错误：系统错误
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> error() {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.ERROR.getCode());
        result.setMessage(ResultMsgEnum.ERROR.getMessage());
        return result;
    }

    /**
     * 失败-自定义信息
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> error(String message) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.ERROR.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 警告-自定义信息
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> warn(String message) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultMsgEnum.WARN.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 警告-自定义状态码和信息
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> warn(int code, String message) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
