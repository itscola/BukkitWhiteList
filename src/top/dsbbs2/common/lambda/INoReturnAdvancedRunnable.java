
package top.dsbbs2.common.lambda;

import java.lang.reflect.Array;

@FunctionalInterface
public interface INoReturnAdvancedRunnable extends INoThrowsRunnable
{
  void run(Object... args) throws Throwable;

  @Override
  default void runInternal() throws Throwable
  {
    this.run(Array.newInstance(Object.class, 0));
  }

  default void invoke(final Object... args)
  {
    try {
      this.run(args);
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
  }

  static void invoke(final INoReturnAdvancedRunnable r, final Object... args)
  {
    r.invoke(args);
  }

  static INoReturnAdvancedRunnable fromRunnable(final Runnable r)
  {
    return args -> r.run();
  }
}
