
package top.dsbbs2.common.lambda;

public final class FinallyCall
{
  private FinallyCall()
  {
  }

  public static void makeFinallyCall(final IThrowsRunnable call, final INoReturnGenericAdvancedRunnable<Throwable> fin)
  {
    FinallyCall.makeFinallyCall(call, fin, true);
  }

  public static void makeFinallyCall(final IThrowsRunnable call, final INoReturnGenericAdvancedRunnable<Throwable> fin,
      final boolean throwOrigin)
  {
    Throwable exc = null;
    try {
      call.runInternal();
    } catch (final Throwable e) {
      exc = e;
    } finally {
      fin.invoke(exc);
      if (throwOrigin && exc!=null) {
        INoThrowsRunnable.ct(exc);
      }
    }
  }
}
