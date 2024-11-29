using System;
using System.Collections.Generic;
using Microsoft.Data.SqlClient;
using System.Text;

namespace ProyADROMv1.DataAccess
{
    public static class DatabaseConnection
    {
        private static readonly string ConnectionString = "Server=10.10.10.99;Database=ProyADROM;User Id=proyadrom;Password=adrom2k7;TrustServerCertificate=True;";

        public static SqlConnection GetConnection()
        {
            return new SqlConnection(ConnectionString);
        }
    }
}
