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

import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.item;
import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.stock;
import static com.oltpbenchmark.benchmarks.chbenchmarkForSpark.chbenchmark.TableNames.supplier;

public class Q16 extends GenericQuery {

    private static final Boolean IF_IS_NEED_COMMIT = false;

    @Override
    public Boolean get_isCommit() {
        return IF_IS_NEED_COMMIT;
    }

    public final SQLStmt query_stmt = new SQLStmt(
            "SELECT i_name, "
                    + "substring(i_data from  1 for 3) AS brand, "
                    + "i_price, "
                    + "count(DISTINCT (mod((s_w_id * s_i_id),10000))) AS supplier_cnt "
                    + "FROM " +stock() + ", "
                    + "" +item() + " "
                    + "WHERE i_id = s_i_id "
                    + "AND i_data NOT LIKE 'zz%' "
                    + "AND (mod((s_w_id * s_i_id),10000) NOT IN "
                    + "(SELECT su_suppkey "
                    + "FROM " +supplier() + " "
                    + "WHERE su_comment LIKE '%bad%')) "
                    + "GROUP BY i_name, "
                    + "substring(i_data from  1 for 3), "
                    + "i_price "
                    + "ORDER BY supplier_cnt DESC"
    );

    protected SQLStmt get_query() {
        return query_stmt;
    }

    public static void main(String[] args) {
        System.out.println(new Q16().query_stmt.getSQL());
    }
}
