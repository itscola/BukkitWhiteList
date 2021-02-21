
package top.dsbbs2.common.lambda;

import java.util.function.Function;

@FunctionalInterface
public interface INoReturnGenericAdvancedRunnable<T> extends Function<T, Void>, INoThrowsRunnable
{
  void run(T arg) throws Throwable;

  @Override
  default Void apply(final T arg)
  {
    this.invoke(arg);
    return null;
  }

  @Override
  default void runInternal() throws Throwable
  {
    this.run(null);
  }

  default void invoke(final T arg)
  {
    try {
      this.run(arg);
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
  }

  static <T> void invoke(final INoReturnGenericAdvancedRunnable<T> r, final T arg)
  {
    r.invoke(arg);
  }

  static <T> INoReturnGenericAdvancedRunnable<T> fromFunction(final Function<T, Void> func)
  {
    return func::apply;
  }
}
