
package top.dsbbs2.common;

import java.lang.reflect.Field;
import java.security.ProtectionDomain;

import sun.misc.Unsafe;
import top.dsbbs2.common.lambda.INoThrowsRunnable;

@SuppressWarnings({ "restriction", "deprecation" })
public final class JVMUnsafe
{
  private JVMUnsafe()
  {
  }

  private static Unsafe UNSAFE;
  static {
    try {
      INoThrowsRunnable.invoke(() ->
      {
        final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        JVMUnsafe.UNSAFE = (Unsafe) theUnsafe.get(null);
      });
    } catch (final Throwable e) {
      //ignore
    }
  }

  public static Unsafe getUnsafe()
  {
    return JVMUnsafe.UNSAFE;
  }

  public static int addressSize()
  {
    return JVMUnsafe.UNSAFE.addressSize();
  }

  public static Object allocateInstance(final Class<?> arg0) throws InstantiationException
  {
    return JVMUnsafe.UNSAFE.allocateInstance(arg0);
  }

  public static long allocateMemory(final long arg0)
  {
    return JVMUnsafe.UNSAFE.allocateMemory(arg0);
  }

  public static int arrayBaseOffset(final Class<?> arg0)
  {
    return JVMUnsafe.UNSAFE.arrayBaseOffset(arg0);
  }

  public static int arrayIndexScale(final Class<?> arg0)
  {
    return JVMUnsafe.UNSAFE.arrayIndexScale(arg0);
  }

  public static boolean compareAndSwapInt(final Object arg0, final long arg1, final int arg2, final int arg3)
  {
    return JVMUnsafe.UNSAFE.compareAndSwapInt(arg0, arg1, arg2, arg3);
  }

  public static boolean compareAndSwapLong(final Object arg0, final long arg1, final long arg2, final long arg3)
  {
    return JVMUnsafe.UNSAFE.compareAndSwapLong(arg0, arg1, arg2, arg3);
  }

  public static boolean compareAndSwapObject(final Object arg0, final long arg1, final Object arg2, final Object arg3)
  {
    return JVMUnsafe.UNSAFE.compareAndSwapObject(arg0, arg1, arg2, arg3);
  }

  public static void copyMemory(final long arg0, final long arg1, final long arg2)
  {
    JVMUnsafe.UNSAFE.copyMemory(arg0, arg1, arg2);
  }

  public static void copyMemory(final Object arg0, final long arg1, final Object arg2, final long arg3, final long arg4)
  {
    JVMUnsafe.UNSAFE.copyMemory(arg0, arg1, arg2, arg3, arg4);
  }

  public static Class<?> defineAnonymousClass(final Class<?> arg0, final byte[] arg1, final Object[] arg2)
  {
    return JVMUnsafe.UNSAFE.defineAnonymousClass(arg0, arg1, arg2);
  }

  public static Class<?> defineClass(final String arg0, final byte[] arg1, final int arg2, final int arg3,
      final ClassLoader arg4, final ProtectionDomain arg5)
  {
    return JVMUnsafe.UNSAFE.defineClass(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  public static void ensureClassInitialized(final Class<?> arg0)
  {
    JVMUnsafe.UNSAFE.ensureClassInitialized(arg0);
  }

  public static int fieldOffset(final Field arg0)
  {
    return JVMUnsafe.UNSAFE.fieldOffset(arg0);
  }

  public static void freeMemory(final long arg0)
  {
    JVMUnsafe.UNSAFE.freeMemory(arg0);
  }

  public static void fullFence()
  {
    JVMUnsafe.UNSAFE.fullFence();
  }

  public static long getAddress(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getAddress(arg0);
  }

  public static int getAndAddInt(final Object arg0, final long arg1, final int arg2)
  {
    return JVMUnsafe.UNSAFE.getAndAddInt(arg0, arg1, arg2);
  }

  public static long getAndAddLong(final Object arg0, final long arg1, final long arg2)
  {
    return JVMUnsafe.UNSAFE.getAndAddLong(arg0, arg1, arg2);
  }

  public static int getAndSetInt(final Object arg0, final long arg1, final int arg2)
  {
    return JVMUnsafe.UNSAFE.getAndSetInt(arg0, arg1, arg2);
  }

  public static long getAndSetLong(final Object arg0, final long arg1, final long arg2)
  {
    return JVMUnsafe.UNSAFE.getAndSetLong(arg0, arg1, arg2);
  }

  public static Object getAndSetObject(final Object arg0, final long arg1, final Object arg2)
  {
    return JVMUnsafe.UNSAFE.getAndSetObject(arg0, arg1, arg2);
  }

  public static boolean getBoolean(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getBoolean(arg0, arg1);
  }

  public static boolean getBoolean(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getBoolean(arg0, arg1);
  }

  public static boolean getBooleanVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getBooleanVolatile(arg0, arg1);
  }

  public static byte getByte(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getByte(arg0);
  }

  public static byte getByte(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getByte(arg0, arg1);
  }

  public static byte getByte(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getByte(arg0, arg1);
  }

  public static byte getByteVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getByteVolatile(arg0, arg1);
  }

  public static char getChar(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getChar(arg0);
  }

  public static char getChar(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getChar(arg0, arg1);
  }

  public static char getChar(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getChar(arg0, arg1);
  }

  public static char getCharVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getCharVolatile(arg0, arg1);
  }

  public static double getDouble(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getDouble(arg0);
  }

  public static double getDouble(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getDouble(arg0, arg1);
  }

  public static double getDouble(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getDouble(arg0, arg1);
  }

  public static double getDoubleVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getDoubleVolatile(arg0, arg1);
  }

  public static float getFloat(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getFloat(arg0);
  }

  public static float getFloat(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getFloat(arg0, arg1);
  }

  public static float getFloat(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getFloat(arg0, arg1);
  }

  public static float getFloatVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getFloatVolatile(arg0, arg1);
  }

  public static int getInt(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getInt(arg0);
  }

  public static int getInt(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getInt(arg0, arg1);
  }

  public static int getInt(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getInt(arg0, arg1);
  }

  public static int getIntVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getIntVolatile(arg0, arg1);
  }

  public static int getLoadAverage(final double[] arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getLoadAverage(arg0, arg1);
  }

  public static long getLong(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getLong(arg0);
  }

  public static long getLong(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getLong(arg0, arg1);
  }

  public static long getLong(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getLong(arg0, arg1);
  }

  public static long getLongVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getLongVolatile(arg0, arg1);
  }

  public static Object getObject(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getObject(arg0, arg1);
  }

  public static Object getObject(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getObject(arg0, arg1);
  }

  public static Object getObjectVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getObjectVolatile(arg0, arg1);
  }

  public static short getShort(final long arg0)
  {
    return JVMUnsafe.UNSAFE.getShort(arg0);
  }

  public static short getShort(final Object arg0, final int arg1)
  {
    return JVMUnsafe.UNSAFE.getShort(arg0, arg1);
  }

  public static short getShort(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getShort(arg0, arg1);
  }

  public static short getShortVolatile(final Object arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.getShortVolatile(arg0, arg1);
  }

  public static void loadFence()
  {
    JVMUnsafe.UNSAFE.loadFence();
  }

  public static void monitorEnter(final Object arg0)
  {
    JVMUnsafe.UNSAFE.monitorEnter(arg0);
  }

  public static void monitorExit(final Object arg0)
  {
    JVMUnsafe.UNSAFE.monitorExit(arg0);
  }

  public static long objectFieldOffset(final Field arg0)
  {
    return JVMUnsafe.UNSAFE.objectFieldOffset(arg0);
  }

  public static int pageSize()
  {
    return JVMUnsafe.UNSAFE.pageSize();
  }

  public static void park(final boolean arg0, final long arg1)
  {
    JVMUnsafe.UNSAFE.park(arg0, arg1);
  }

  public static void putAddress(final long arg0, final long arg1)
  {
    JVMUnsafe.UNSAFE.putAddress(arg0, arg1);
  }

  public static void putBoolean(final Object arg0, final int arg1, final boolean arg2)
  {
    JVMUnsafe.UNSAFE.putBoolean(arg0, arg1, arg2);
  }

  public static void putBoolean(final Object arg0, final long arg1, final boolean arg2)
  {
    JVMUnsafe.UNSAFE.putBoolean(arg0, arg1, arg2);
  }

  public static void putBooleanVolatile(final Object arg0, final long arg1, final boolean arg2)
  {
    JVMUnsafe.UNSAFE.putBooleanVolatile(arg0, arg1, arg2);
  }

  public static void putByte(final long arg0, final byte arg1)
  {
    JVMUnsafe.UNSAFE.putByte(arg0, arg1);
  }

  public static void putByte(final Object arg0, final int arg1, final byte arg2)
  {
    JVMUnsafe.UNSAFE.putByte(arg0, arg1, arg2);
  }

  public static void putByte(final Object arg0, final long arg1, final byte arg2)
  {
    JVMUnsafe.UNSAFE.putByte(arg0, arg1, arg2);
  }

  public static void putByteVolatile(final Object arg0, final long arg1, final byte arg2)
  {
    JVMUnsafe.UNSAFE.putByteVolatile(arg0, arg1, arg2);
  }

  public static void putChar(final long arg0, final char arg1)
  {
    JVMUnsafe.UNSAFE.putChar(arg0, arg1);
  }

  public static void putChar(final Object arg0, final int arg1, final char arg2)
  {
    JVMUnsafe.UNSAFE.putChar(arg0, arg1, arg2);
  }

  public static void putChar(final Object arg0, final long arg1, final char arg2)
  {
    JVMUnsafe.UNSAFE.putChar(arg0, arg1, arg2);
  }

  public static void putCharVolatile(final Object arg0, final long arg1, final char arg2)
  {
    JVMUnsafe.UNSAFE.putCharVolatile(arg0, arg1, arg2);
  }

  public static void putDouble(final long arg0, final double arg1)
  {
    JVMUnsafe.UNSAFE.putDouble(arg0, arg1);
  }

  public static void putDouble(final Object arg0, final int arg1, final double arg2)
  {
    JVMUnsafe.UNSAFE.putDouble(arg0, arg1, arg2);
  }

  public static void putDouble(final Object arg0, final long arg1, final double arg2)
  {
    JVMUnsafe.UNSAFE.putDouble(arg0, arg1, arg2);
  }

  public static void putDoubleVolatile(final Object arg0, final long arg1, final double arg2)
  {
    JVMUnsafe.UNSAFE.putDoubleVolatile(arg0, arg1, arg2);
  }

  public static void putFloat(final long arg0, final float arg1)
  {
    JVMUnsafe.UNSAFE.putFloat(arg0, arg1);
  }

  public static void putFloat(final Object arg0, final int arg1, final float arg2)
  {
    JVMUnsafe.UNSAFE.putFloat(arg0, arg1, arg2);
  }

  public static void putFloat(final Object arg0, final long arg1, final float arg2)
  {
    JVMUnsafe.UNSAFE.putFloat(arg0, arg1, arg2);
  }

  public static void putFloatVolatile(final Object arg0, final long arg1, final float arg2)
  {
    JVMUnsafe.UNSAFE.putFloatVolatile(arg0, arg1, arg2);
  }

  public static void putInt(final long arg0, final int arg1)
  {
    JVMUnsafe.UNSAFE.putInt(arg0, arg1);
  }

  public static void putInt(final Object arg0, final int arg1, final int arg2)
  {
    JVMUnsafe.UNSAFE.putInt(arg0, arg1, arg2);
  }

  public static void putInt(final Object arg0, final long arg1, final int arg2)
  {
    JVMUnsafe.UNSAFE.putInt(arg0, arg1, arg2);
  }

  public static void putIntVolatile(final Object arg0, final long arg1, final int arg2)
  {
    JVMUnsafe.UNSAFE.putIntVolatile(arg0, arg1, arg2);
  }

  public static void putLong(final long arg0, final long arg1)
  {
    JVMUnsafe.UNSAFE.putLong(arg0, arg1);
  }

  public static void putLong(final Object arg0, final int arg1, final long arg2)
  {
    JVMUnsafe.UNSAFE.putLong(arg0, arg1, arg2);
  }

  public static void putLong(final Object arg0, final long arg1, final long arg2)
  {
    JVMUnsafe.UNSAFE.putLong(arg0, arg1, arg2);
  }

  public static void putLongVolatile(final Object arg0, final long arg1, final long arg2)
  {
    JVMUnsafe.UNSAFE.putLongVolatile(arg0, arg1, arg2);
  }

  public static void putObject(final Object arg0, final int arg1, final Object arg2)
  {
    JVMUnsafe.UNSAFE.putObject(arg0, arg1, arg2);
  }

  public static void putObject(final Object arg0, final long arg1, final Object arg2)
  {
    JVMUnsafe.UNSAFE.putObject(arg0, arg1, arg2);
  }

  public static void putObjectVolatile(final Object arg0, final long arg1, final Object arg2)
  {
    JVMUnsafe.UNSAFE.putObjectVolatile(arg0, arg1, arg2);
  }

  public static void putOrderedInt(final Object arg0, final long arg1, final int arg2)
  {
    JVMUnsafe.UNSAFE.putOrderedInt(arg0, arg1, arg2);
  }

  public static void putOrderedLong(final Object arg0, final long arg1, final long arg2)
  {
    JVMUnsafe.UNSAFE.putOrderedLong(arg0, arg1, arg2);
  }

  public static void putOrderedObject(final Object arg0, final long arg1, final Object arg2)
  {
    JVMUnsafe.UNSAFE.putOrderedObject(arg0, arg1, arg2);
  }

  public static void putShort(final long arg0, final short arg1)
  {
    JVMUnsafe.UNSAFE.putShort(arg0, arg1);
  }

  public static void putShort(final Object arg0, final int arg1, final short arg2)
  {
    JVMUnsafe.UNSAFE.putShort(arg0, arg1, arg2);
  }

  public static void putShort(final Object arg0, final long arg1, final short arg2)
  {
    JVMUnsafe.UNSAFE.putShort(arg0, arg1, arg2);
  }

  public static void putShortVolatile(final Object arg0, final long arg1, final short arg2)
  {
    JVMUnsafe.UNSAFE.putShortVolatile(arg0, arg1, arg2);
  }

  public static long reallocateMemory(final long arg0, final long arg1)
  {
    return JVMUnsafe.UNSAFE.reallocateMemory(arg0, arg1);
  }

  public static void setMemory(final long arg0, final long arg1, final byte arg2)
  {
    JVMUnsafe.UNSAFE.setMemory(arg0, arg1, arg2);
  }

  public static void setMemory(final Object arg0, final long arg1, final long arg2, final byte arg3)
  {
    JVMUnsafe.UNSAFE.setMemory(arg0, arg1, arg2, arg3);
  }

  public static boolean shouldBeInitialized(final Class<?> arg0)
  {
    return JVMUnsafe.UNSAFE.shouldBeInitialized(arg0);
  }

  public static Object staticFieldBase(final Class<?> arg0)
  {
    return JVMUnsafe.UNSAFE.staticFieldBase(arg0);
  }

  public static Object staticFieldBase(final Field arg0)
  {
    return JVMUnsafe.UNSAFE.staticFieldBase(arg0);
  }

  public static long staticFieldOffset(final Field arg0)
  {
    return JVMUnsafe.UNSAFE.staticFieldOffset(arg0);
  }

  public static void storeFence()
  {
    JVMUnsafe.UNSAFE.storeFence();
  }

  public static void throwException(final Throwable arg0)
  {
    JVMUnsafe.UNSAFE.throwException(arg0);
  }

  public static boolean tryMonitorEnter(final Object arg0)
  {
    return JVMUnsafe.UNSAFE.tryMonitorEnter(arg0);
  }

  public static void unpark(final Object arg0)
  {
    JVMUnsafe.UNSAFE.unpark(arg0);
  }
}
