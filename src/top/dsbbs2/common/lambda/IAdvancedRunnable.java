
package top.dsbbs2.common.lambda;

import java.lang.reflect.Array;

@FunctionalInterface
public interface IAdvancedRunnable<R> extends INoThrowsRunnable
{
  R run(Object... args) throws Throwable;

  @Override
  default void runInternal() throws Throwable
  {
    this.run(Array.newInstance(Object.class, 0));
  }

  default R invoke(final Object... args)
  {
    try {
      return this.run(args);
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
    return null;
  }

  static <R> R invoke(final IAdvancedRunnable<R> r, final Object... args)
  {
    return r.invoke(args);
  }

  static IAdvancedRunnable<Void> fromRunnable(final Runnable r)
  {
    return args ->
    {
      r.run();
      return null;
    };
  }
}
