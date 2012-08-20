/*
 * The MIT License
 *
 *  Copyright (c) 2012, benas (md.benhassine@gmail.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package net.benas.cb4j.core.validator.test;

import net.benas.cb4j.core.model.Field;
import net.benas.cb4j.core.validator.DateFormatFieldValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests class for {@link DateFormatFieldValidator}
 * @author benas (md.benhassine@gmail.com)
 */
public class DateFormatFieldValidatorTest {

    private DateFormatFieldValidator dateFormatFieldValidator;
    private Field field;

    @Before
    public void setUp() throws Exception {
        dateFormatFieldValidator = new DateFormatFieldValidator("yyyy/MM/dd");
        field = new Field(1,null);
    }

    @After
    public void tearDown() throws Exception {
        dateFormatFieldValidator = null;
        field = null;
        System.gc();
    }

    @Test
    public void testIsValidFieldOK() throws Exception {
        field.setContent("2011/08/24");
        assertTrue(dateFormatFieldValidator.isValidField(field));
    }

    @Test
    public void testIsValidFieldKO() throws Exception {
        field.setContent("20111212");
        assertFalse(dateFormatFieldValidator.isValidField(field));
    }

}