
package top.dsbbs2.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import top.dsbbs2.common.lambda.IGenericAdvancedRunnable;
import top.dsbbs2.common.lambda.INoThrowsRunnable;

public final class FileUtils
{
    private FileUtils()
    {

    }

    public static byte[] readFile(final File f)
    {
        return IGenericAdvancedRunnable.invoke(awa ->
        {
            if (!f.isFile()) {
                return new byte[0];
            }
            try (FileInputStream is = new FileInputStream(f)) {
                final byte[] buf = new byte[is.available()];
                is.read(buf);
                return buf;
            }
        }, null);
    }

    public static String readTextFile(final File f, final Charset charset)
    {
        return new String(FileUtils.readFile(f), charset);
    }

    public static void writeFile(final File f, final byte[] content, final boolean append)
    {
        INoThrowsRunnable.invoke(() ->
        {
            if (!f.isFile()) {
                Optional.ofNullable(f.getAbsoluteFile().getParentFile()).ifPresent(File::mkdirs);
                f.createNewFile();
            }
            try (FileOutputStream os = new FileOutputStream(f, append)) {
                os.write(content);
                os.flush();
            }
        });
    }

    public static void writeTextFile(final File f, final String str, final Charset charset, final boolean append)
    {
        FileUtils.writeFile(f, str.getBytes(charset), append);
    }

}
