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

package com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.queries;

import com.oltpbenchmark.api.SQLStmt;

import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.customer;
import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.nation;
import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.oorder;
import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.order_line;

public class Q10 extends GenericQuery {

    private static final Boolean IF_IS_NEED_COMMIT = false;

    @Override
    public Boolean get_isCommit() {
        return IF_IS_NEED_COMMIT;
    }

    public final SQLStmt query_stmt = new SQLStmt(
            "SELECT c_id, "
                    + "c_last, "
                    + "sum(ol_amount) AS revenue, "
                    + "c_city, "
                    + "c_phone, "
                    + "n_name "
                    + "FROM " +customer() + ", "
                    + "" +oorder() + ", "
                    + "" +order_line() + ", "
                    + "" +nation() + " "
                    + "WHERE c_id = o_c_id "
                    + "AND c_w_id = o_w_id "
                    + "AND c_d_id = o_d_id "
                    + "AND ol_w_id = o_w_id "
                    + "AND ol_d_id = o_d_id "
                    + "AND ol_o_id = o_id "
                    + "AND o_entry_d >= TIMESTAMP '2007-01-02 00:00:00.000000' "
                    + "AND o_entry_d <= ol_delivery_d "
                    + "AND n_nationkey = ascii(substring(c_state from  1  for  1)) "
                    + "GROUP BY c_id, "
                    + "c_last, "
                    + "c_city, "
                    + "c_phone, "
                    + "n_name "
                    + "ORDER BY revenue DESC"
    );

    protected SQLStmt get_query() {
        return query_stmt;
    }

    public static void main(String[] args) {
        System.out.println(new Q10().query_stmt.getSQL());
    }
}
