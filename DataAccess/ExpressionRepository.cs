using ProyADROMv1.Entities;
using System;
using System.Collections.Generic;
using System.Data;
using Microsoft.Data.SqlClient;
using System.Text;

namespace ProyADROMv1.DataAccess
{
    public class ExpressionRepository
    {
        public List<Expression> GetAllExpressions()
        {
            var expressions = new List<Expression>();

            using (var connection = DatabaseConnection.GetConnection())
            {
                connection.Open();
                using (var command = new SqlCommand("SP_TTEXPS_EXCL_Q01", connection))
                {
                    command.CommandType = CommandType.StoredProcedure;

                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            expressions.Add(new Expression
                            {
                                Text = reader["DE_EXPS"].ToString(),
                                Length = reader["DE_EXPS"].ToString().Length
                            });
                        }
                    }
                }
            }

            return expressions;
        }

        public void AddExpression(string expression)
        {
            using (var connection = DatabaseConnection.GetConnection())
            {
                connection.Open();
                using (var command = new SqlCommand("SP_TTEXPS_EXCL_I01", connection))
                {
                    command.CommandType = CommandType.StoredProcedure;
                    command.Parameters.Add(new SqlParameter("@ISDE_EXPS", SqlDbType.VarChar, 100)).Value = expression.ToUpper();

                    command.ExecuteNonQuery();
                }
            }
        }
    }
}