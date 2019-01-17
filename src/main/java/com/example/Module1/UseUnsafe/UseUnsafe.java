package com.example.demo.UseUnsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *
 * java无法直接操作本地操作系统内存，但是可以通过java方法进行操作，就是使用unsafe类
 * unsafe类有四个非常强大的功能：
 *  1.直接操作本地操作系统的类型
 *      a.分配内存 allocateMemory
 *      b.扩充内存 reallocateMemory
 *      c.释放内存 freeMemory
 *  2.字段的定位与修改字段的值
 *      可以定位对象中的某个字段在内存的哪个位置，并且可以修改该字段的值。即使它是私有的
 *  3.线程的挂起与恢复
 *      a.将线程挂起其实是通过调用park方法来实现的，调用后，线程会一直阻塞到超时或者终端条件出现
 *      b.将线程恢复运行是通过调用unpark方法
 *      c.并发框架中，将对线程的大部分操作都封装在lockSupport类，lockSupport中提供各种pack方法，但最终都是调用了Unsafe.pack
 *  4.cas操作（乐观锁）
 *      a.Compare And Swap，简单来说就是 比较并交换
 *      b.cas操作包含三个操作数：内存的位置，期望原值，更新值
 *      c.将内存位置的值与期望原值进行比较，如果相同就将更新值替换为期望原值并返回true，如果不相同就返回false，不修改；
 *
 * Created by Administrator on 2018/12/2 0002.
 */
public class UseUnsafe {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);

        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.err.println(unsafe);

        byte[] data = new byte[10];
        System.err.println(Arrays.toString(data));

        int bateArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
        System.err.println("byte[]数组第一个元素的基础偏移量： " + bateArrayBaseOffset);

        // 设置指定的字节数组，修改其内存的字段的位置
        // 1.在data这个数组对象中，将其第一个位置的值设置为1,
        // 基础偏移量为该数组对象在内存中的起始位置
        unsafe.putByte(data, bateArrayBaseOffset, (byte) 1);
        // 2.将data这个数组对象的第7个位置的值设置为8
        unsafe.putByte(data, bateArrayBaseOffset + 6, (byte) 8);
        System.err.println(Arrays.toString(data));

    }


}
