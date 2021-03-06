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
package org.jeasy.batch.core.listener;

import org.jeasy.batch.core.record.Record;

/**
 * Enables the implementing class to get notified before/after the processing pipeline.
 *
 * @author Mario Mueller (mario@xenji.com)
 */
// This interface is not generic (that is PipelineListener<P>) because the record type can change in pipeline
public interface PipelineListener {

    /**
     * Called before the record gets processed.
     * If you create a new record, you <strong>must</strong> keep the original header of the modified record.
     *
     * @param record The record that will be processed.
     * @return the pre-processed record
     */
    default <P> Record<P> beforeRecordProcessing(final Record<P> record) {
        return record;
    }

    /**
     * Called after the record has been processed.
     *
     * @param inputRecord  The record to process.
     * @param outputRecord The processed record. <strong>May be {@code null} if the record has been filtered</strong>
     */
    default <P> void afterRecordProcessing(final Record<P> inputRecord, final Record<P> outputRecord) {
        // no-op
    }

    /**
     * Called when an exception occurs during record processing
     *
     * @param record    the record attempted to be processed
     * @param throwable the throwable that was thrown during record processing
     */
    default <P> void onRecordProcessingException(final Record<P> record, final Throwable throwable) {
        // no-op
    }

}
