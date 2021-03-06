/*
 * The MIT License
 *
 *   Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.batch.extensions.opencsv;

import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.record.StringRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpenCsvRecordMarshallerTest {

    @Mock
    private Header header;
    @Mock
    private Record<Foo> record;

    private OpenCsvRecordMarshaller<Foo> marshaller;

    @Before
    public void setUp() {
        marshaller = new OpenCsvRecordMarshaller<>(Foo.class, "firstName", "lastName", "age", "married");
        Foo foo = new Foo("foo", "bar", 30, true);
        when(record.getHeader()).thenReturn(header);
        when(record.getPayload()).thenReturn(foo);
    }

    @Test
    public void processRecord() throws Exception {
        String expectedPayload = "\"foo\",\"bar\",\"30\",\"true\"";
        StringRecord actual = marshaller.processRecord(record);

        assertThat(actual.getHeader()).isEqualTo(header);
        assertThat(actual.getPayload()).isEqualTo(expectedPayload);
    }

}
