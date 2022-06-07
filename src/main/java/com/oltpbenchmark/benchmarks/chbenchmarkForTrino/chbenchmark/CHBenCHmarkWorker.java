/*
 * Copyright 2020 by OLTPBenchmark Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.oltpbenchmark.benchmarks.chbenchmarkForTrino.chbenchmark;

import com.oltpbenchmark.api.Procedure.UserAbortException;
import com.oltpbenchmark.api.TransactionType;
import com.oltpbenchmark.api.Worker;
import com.oltpbenchmark.benchmarks.chbenchmarkForTrino.chbenchmark.queries.GenericQuery;
import com.oltpbenchmark.types.TransactionStatus;
import java.sql.Connection;
import java.sql.SQLException;

public class CHBenCHmarkWorker extends Worker<CHBenCHmark> {
    public CHBenCHmarkWorker(CHBenCHmark benchmarkModule, int id) {
        super(benchmarkModule, id);
    }

    @Override
    protected TransactionStatus executeWork(Connection conn, TransactionType nextTransaction) throws UserAbortException, SQLException {
        try {
            GenericQuery proc = (GenericQuery) this.getProcedure(nextTransaction.getProcedureClass());
            proc.run(conn);
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }

        return (TransactionStatus.SUCCESS);

    }
}
