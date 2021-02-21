
package top.dsbbs2.common.lambda;

@FunctionalInterface
public interface IThrowsRunnable extends Runnable
{
  void runInternal() throws Throwable;

  @Override
  default void run()
  {
    try {
      this.runInternal();
    } catch (final Throwable e) {
      INoThrowsRunnable.ct(e);
    }
  }

  static void invoke(final IThrowsRunnable r) throws Throwable
  {
    r.runInternal();
  }

  static IThrowsRunnable fromRunnable(final Runnable r)
  {
    return r::run;
  }
  
  static IThrowsRunnable fromNoThrowsRunnable(final INoThrowsRunnable r)
  {
    return r::runInternal;
  }
}
