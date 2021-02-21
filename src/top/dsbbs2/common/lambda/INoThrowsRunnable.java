
package top.dsbbs2.common.lambda;


import top.dsbbs2.common.JVMUnsafe;

@FunctionalInterface
public interface INoThrowsRunnable extends Runnable
{
  void runInternal() throws Throwable;

  static void ct(final Throwable e)
  {
    if (e instanceof Error) {
      throw (Error) e;
    } else if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else {
      if(JVMUnsafe.getUnsafe()==null)
        throw new RuntimeException(e);
      else JVMUnsafe.throwException(e);
    }
  }

  @Override
  default void run()
  {
    this.invoke();
  }

  default void invoke()
  {
    try {
      this.runInternal();
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
  }

  static void invoke(final INoThrowsRunnable r)
  {
    r.invoke();
  }

  static INoThrowsRunnable fromRunnable(final Runnable r)
  {
    return r::run;
  }
}
