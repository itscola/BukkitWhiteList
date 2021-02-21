
package top.dsbbs2.common.lambda;

import java.util.function.Function;

@FunctionalInterface
public interface IGenericAdvancedRunnable<T, R> extends Function<T, R>, INoThrowsRunnable
{
  R run(T arg) throws Throwable;

  @Override
  default R apply(final T arg)
  {
    return this.invoke(arg);
  }

  @Override
  default void runInternal() throws Throwable
  {
    this.run(null);
  }

  default R invoke(final T arg)
  {
    try {
      return this.run(arg);
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
    return null;
  }

  static <T, R> R invoke(final IGenericAdvancedRunnable<T, R> r, final T arg)
  {
    return r.invoke(arg);
  }

  static <T, R> IGenericAdvancedRunnable<T, R> fromFunction(final Function<T, R> func)
  {
    return func::apply;
  }
}
