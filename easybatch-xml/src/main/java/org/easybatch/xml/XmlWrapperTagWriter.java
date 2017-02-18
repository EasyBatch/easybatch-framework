/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.easybatch.xml;

import org.easybatch.core.job.JobParameters;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.listener.JobListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static org.easybatch.core.util.Utils.LINE_SEPARATOR;
import static org.easybatch.core.util.Utils.checkNotNull;

/**
 * Utility class to write an Xml declaration and a configurable wrapper tag to an output stream.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class XmlWrapperTagWriter implements JobListener {

    private static final Logger LOGGER = Logger.getLogger(XmlWrapperTagWriter.class.getSimpleName());

    private String version = "1.0";

    private String encoding = "UTF-8";

    private boolean standalone = true;

    private String wrapperTag;

    private File file;

    /**
     * Create a xml wrapper element writer.
     *
     * @param file       target file
     * @param wrapperTag the wrapper tag to write around xml content
     */
    public XmlWrapperTagWriter(final File file, final String wrapperTag) {
        checkNotNull(file, "file");
        this.wrapperTag = wrapperTag;
        this.file = file;
    }

    /**
     * Create a xml wrapper element writer.
     *
     * @param file       target file
     * @param wrapperTag the wrapper tag to write around xml content
     * @param version    the xml version
     */
    public XmlWrapperTagWriter(final File file, final String wrapperTag, final String version) {
        this(file, wrapperTag);
        checkNotNull(version, "version");
        this.version = version;
    }

    /**
     * Create a xml wrapper element writer.
     *
     * @param file       target file
     * @param wrapperTag the wrapper tag to write around xml content
     * @param version    the xml version
     * @param encoding   the xml encoding
     */
    public XmlWrapperTagWriter(final File file, final String wrapperTag, final String version, final String encoding) {
        this(file, wrapperTag, version);
        checkNotNull(encoding, "encoding");
        this.encoding = encoding;
    }

    /**
     * Create a xml wrapper element writer.
     *
     * @param file       target file
     * @param wrapperTag the wrapper tag to write around xml content
     * @param version    the xml version
     * @param encoding   the xml encoding
     * @param standalone true if the xml is standalone
     */
    public XmlWrapperTagWriter(final File file, final String wrapperTag, final String version, final String encoding, final boolean standalone) {
        this(file, wrapperTag, version, encoding);
        this.standalone = standalone;
    }

    @Override
    public void beforeJobStart(final JobParameters jobParameters) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(format("<?xml version=\"%s\" encoding=\"%s\" standalone=\"%s\"?>", version, encoding, standalone ? "yes" : "no"));
            fileWriter.write(LINE_SEPARATOR);
            fileWriter.write("<" + wrapperTag + ">");
            fileWriter.write(LINE_SEPARATOR);
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to write XML declaration and wrapper opening tag to file " + file.getAbsolutePath(), e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Unable to close file writer", e);
                }
            }
        }
    }

    @Override
    public void afterJobEnd(final JobReport jobReport) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write("</" + wrapperTag + ">");
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to write closing wrapper tag to file " + file.getAbsolutePath(), e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Unable to close file writer", e);
                }
            }
        }
    }
}
