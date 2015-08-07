/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package org.easybatch.core.field;

import org.easybatch.core.beans.Gender;
import org.easybatch.core.beans.Person;
import org.easybatch.core.record.StringRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link BeanRecordFieldExtractor.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class BeanRecordFieldExtractorTest {

    private Person record;
    private BeanRecordFieldExtractor fieldExtractor;

    @Before
    public void setUp() throws Exception {
        record = new Person();
        record.setFirstName("myFirstName");
        record.setLastName("myLastName");
        record.setBirthDate(new Date());
        record.setAge(33);
        record.setGender(Gender.FEMALE);
        record.setMarried(true);

    }

    @Test
    public void whenFieldsIsEmpty_ThenItShouldExtractAllPropertiesValues() throws Exception {
        fieldExtractor = new BeanRecordFieldExtractor(Person.class);
        Iterable<?> values = fieldExtractor.extractFields(record);
        assertThat(values).hasSize(6);
        assertThat(values).containsOnlyOnce(
                record.getFirstName(), record.getLastName(), record.getBirthDate(),
                record.getAge(), record.getGender(), record.isMarried());
    }

    @Test
    public void whenFieldsIsNotEmpty_ThenItShouldExtractAllPropertiesValuesInRightOrder() throws Exception {
        BeanRecordFieldExtractor fieldExtractor = new BeanRecordFieldExtractor(Person.class, "lastName", "age", "married");
        Iterable<?> values = fieldExtractor.extractFields(record);
        assertThat(values).hasSize(3);
        Iterator<?> iterator = values.iterator();
        assertThat(iterator.next()).isEqualTo(record.getLastName());
        assertThat(iterator.next()).isEqualTo(record.getAge());
        assertThat(iterator.next()).isEqualTo(record.isMarried());

    }

}
