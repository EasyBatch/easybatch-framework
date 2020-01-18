/**
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.batch.flatfile;

import org.jeasy.batch.core.reader.AbstractFileRecordReader;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.StringRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Date;

/**
 * A {@link RecordReader} implementation that reads data from a flat file.
 *
 * This reader produces {@link StringRecord} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FlatFileRecordReader extends AbstractFileRecordReader {

    private BufferedReader bufferedReader;
    private long currentRecordNumber;

    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param fileName to read records from
     * @deprecated This constructor is deprecated since v5.3 and will be removed in v6.
     * Use {@link FlatFileRecordReader#FlatFileRecordReader(java.nio.file.Path)} instead
     */
    @Deprecated
    public FlatFileRecordReader(final String fileName) {
        this(fileName, Charset.defaultCharset().name());
    }

    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param fileName    to read records from
     * @param charsetName of the input file
     * @deprecated This constructor is deprecated since v5.3 and will be removed in v6.
     * Use {@link FlatFileRecordReader#FlatFileRecordReader(java.nio.file.Path, java.nio.charset.Charset)} instead
     */
    @Deprecated
    public FlatFileRecordReader(final String fileName, final String charsetName) {
        this(new File(fileName), charsetName);
    }

    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param input to read records from
     *
     * @deprecated This constructor is deprecated since v5.3 and will be removed in v6.
     * Use {@link FlatFileRecordReader#FlatFileRecordReader(java.nio.file.Path)} instead
     */
    @Deprecated
    public FlatFileRecordReader(final File input) {
        this(input, Charset.defaultCharset().name());
    }

    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param input       to read records from
     * @param charsetName of the input file
     *
     * @deprecated This constructor is deprecated since v5.3 and will be removed in v6.
     * Use {@link FlatFileRecordReader#FlatFileRecordReader(java.nio.file.Path, java.nio.charset.Charset)} instead
     */
    @Deprecated
    public FlatFileRecordReader(final File input, final String charsetName) {
        super(input, Charset.forName(charsetName));
    }


    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param path of the file to read records from
     */
    public FlatFileRecordReader(final Path path) {
        this(path, Charset.defaultCharset());
    }


    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param path of the file to read records from
     * @param charsetName of the input file
     * @deprecated This constructor is deprecated since v5.3 and will be removed in v6.
     * Use {@link FlatFileRecordReader#FlatFileRecordReader(java.nio.file.Path, java.nio.charset.Charset)} instead
     */
    @Deprecated
    public FlatFileRecordReader(final Path path, final String charsetName) {
        super(path, Charset.forName(charsetName));
    }

    /**
     * Create a new {@link FlatFileRecordReader}.
     *
     * @param path of the file to read records from
     * @param charset of the input file
     */
    public FlatFileRecordReader(final Path path, final Charset charset) {
        super(path, charset);
    }

    @Override
    public StringRecord readRecord() throws IOException {
        Header header = new Header(++currentRecordNumber, getDataSourceName(), new Date());
        String line = bufferedReader.readLine();
        if (line != null) {
            return new StringRecord(header, line);
        } else {
            return null;
        }
    }

    @Override
    public void open() throws Exception {
        currentRecordNumber = 0;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    @Override
    public void close() throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    private String getDataSourceName() {
        return file.getAbsolutePath();
    }

}
