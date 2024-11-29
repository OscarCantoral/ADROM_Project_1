using Microsoft.Data.SqlClient;
using ProyADROMv1.Entities;
using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace ProyADROMv1.DataAccess
{
    public class TpplbrRepository
    {
        public List<Expression> GetPalabrasBD()
        {
            var expressions = new List<Expression>();

            using (var connection = DatabaseConnection.GetConnection())
            {
                connection.Open();
                using (var command = new SqlCommand("SP_TPPLBR_CLAV_Q01", connection))
                {
                    command.CommandType = CommandType.StoredProcedure;

                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            expressions.Add(new Expression
                            {
                                Text = reader["DE_PLBR"].ToString()
                            });
                        }
                    }
                }
            }

            return expressions;
        }
    }
}
