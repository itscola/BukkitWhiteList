
package top.dsbbs2.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import top.dsbbs2.common.lambda.IAdvancedRunnable;
import top.dsbbs2.common.lambda.IGenericAdvancedRunnable;
import top.dsbbs2.common.lambda.INoThrowsRunnable;

public final class DB
{
  public final String database;
  public final String user;
  public final String password;
  public final String address;
  public final int port;
  private Connection connection;

  public DB(final String address, final int port, final String database, final String user, final String password)
  {
    this.address = address;
    this.port = port;
    this.database = database;
    this.user = user;
    this.password = password;
    this.connection = this.reopenConnection();
  }

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (final ClassNotFoundException ignore) {
      IAdvancedRunnable.invoke(args -> Class.forName("com.mysql.jdbc.Driver"));
    }
  }

  public static void ensureInitialized()
  {
    // none
  }

  public Connection getConnection()
  {
    return this.connection;
  }

  public Connection reopenConnection()
  {
    Optional.ofNullable(this.connection).ifPresent(i ->
    {
      if (IGenericAdvancedRunnable.invoke(i2 -> !i.isClosed(), null)) {
        INoThrowsRunnable.invoke(i::close);
      }
    });
    return IAdvancedRunnable
        .invoke(args -> this.connection = DriverManager.getConnection("jdbc:mysql://" + this.address + ":" + this.port
            + "/" + this.database + "?characterEncoding=utf-8&serverTimezone=GMT%2B8", this.user, this.password));
  }

  public <R> R fastQuery(final IGenericAdvancedRunnable<Connection, R> func)
  {
    return IAdvancedRunnable.invoke(args -> func.invoke(this.connection));
  }

  public boolean fastPreparedExecute(final String sql, final Object... args)
  {
    return this.fastQuery(con ->
    {
      try (final PreparedStatement prepareStatement = con.prepareStatement(sql)) {
        for (int i = 1; i <= args.length; i++) {
          prepareStatement.setObject(i, args[i - 1]);
        }
        return prepareStatement.execute();
      }
    });
  }

  public int fastPreparedExecuteUpdate(final String sql, final Object... args)
  {
    return this.fastQuery(con ->
    {
      try (final PreparedStatement prepareStatement = con.prepareStatement(sql)) {
        for (int i = 1; i <= args.length; i++) {
          prepareStatement.setObject(i, args[i - 1]);
        }
        return prepareStatement.executeUpdate();
      }
    });
  }

  public <R> R fastPreparedExecuteQuery(final String sql, final IGenericAdvancedRunnable<ResultSet, R> func,
      final Object... args)
  {
    return this.fastQuery(con ->
    {
      try (final PreparedStatement prepareStatement = con.prepareStatement(sql)) {
        for (int i = 1; i <= args.length; i++) {
          prepareStatement.setObject(i, args[i - 1]);
        }
        try (final ResultSet r = prepareStatement.executeQuery()) {
          return func.invoke(r);
        }
      }
    });
  }
}
