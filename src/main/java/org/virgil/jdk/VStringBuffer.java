package org.virgil.jdk;

import java.io.Serializable;

/**
 * Created by sunvirgil on 2017/3/22.
 */
public class VStringBuffer implements Serializable {

    private static final long serialVersionUID = 1L;
    private char[] value;
    private int len;

    public VStringBuffer(int size) {
        this.value = new char[size < 16 ? 16 : size];
        this.len = 0;
    }

    public VStringBuffer() {
        this(16);
    }

    public VStringBuffer(String str) {
        this(str.length() + 16);

    }

    public int length() {
        return this.len;
    }

    //返回指定位置的字符
    public synchronized char charAt(int i) {
        if (i < 0 || i > this.len) {
            throw new StringIndexOutOfBoundsException(i);
        }
        return this.value[i];
    }

    public void setChar(int i, char ch) {
        if (i < 0 || i > this.len) {
            throw new StringIndexOutOfBoundsException(i);
        }
        this.value[i] = ch;
    }

    public synchronized String toString() {
        return new String(this.value, 0, this.len);
    }

    //指定位置插入字符串
    public synchronized VStringBuffer insert(int i, VStringBuffer str) {
        if (i < 0) i = 0;
        if (i > this.len) i = len;
        if (str == null) return this;
        char[] temp = this.value;
        if ((this.value.length - this.len) < str.len) {
            //扩容
            this.value = new char[this.value.length + str.len * 2];
            for (int j = 0; j < i; j++) {
                this.value[j] = temp[j];
            }
        }
        for (int j = 0; j < str.len; j++) {
            this.value[i + j] = str.value[j];
        }
        for (int j = i; j < this.len; j++) {
            this.value[j + str.len] = temp[j];
        }
        this.len += str.len;
        return this;
    }

    public synchronized VStringBuffer insert(int i, String str) {
        if (i < 0) i = 0;
        if (i > this.len) i = len;
        if (str == null) return this;
        char[] temp = this.value;
        if ((this.value.length - this.len) < str.length()) {
            //扩容
            this.value = new char[this.value.length + str.length() * 2];
            for (int j = 0; j < i; j++) {
                this.value[j] = temp[j];
            }
        }
        for (int j = 0; j < str.length(); j++) {
            this.value[i + j] = str.charAt(j);
        }
        for (int j = i; j < str.length(); j++) {
            this.value[str.length() + j] = temp[j];
        }
        return this;
    }

    public synchronized VStringBuffer insert(int i, boolean b) {
        return this.insert(i, b ? "true" : "false");
    }

    public synchronized VStringBuffer append(String str) {
        return this.insert(this.len, (str == null) ? "null" : str);
    }


    public synchronized VStringBuffer delete(int begin, int end) {
        if (begin < 0) begin = 0;
        if (end > this.len) end = this.len;
        if (begin > end) throw new StringIndexOutOfBoundsException(begin - end);
        for (int i = 0; i < this.len - end; i++) {
            this.value[begin + i] = this.value[end + i];
        }
        this.len -= end - begin;
        return this;
    }

}
