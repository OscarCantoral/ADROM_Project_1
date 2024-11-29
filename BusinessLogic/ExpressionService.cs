using ProyADROMv1.DataAccess;
using ProyADROMv1.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace ProyADROMv1.BusinessLogic
{
    public class ExpressionService
    {
        private readonly ExpressionRepository _repository;

        // Constructor
        public ExpressionService()
        {
            _repository = new ExpressionRepository(); // Inicialización manual
        }

        public List<Expression> GetExpressions()
        {
            return _repository.GetAllExpressions();
        }

        public void AddExpression(string expression)
        {
            if (!string.IsNullOrWhiteSpace(expression))
            {
                _repository.AddExpression(expression);
            }
        }

        public string CleanText(string input, List<Expression> excludedExpressions)
        {
            foreach (var expression in excludedExpressions)
            {
                input = input.Replace(expression.Text, "", System.StringComparison.OrdinalIgnoreCase);
            }

            return input.Trim();
        }

        // Método adicional para limpiar el texto utilizando la base de datos
        public string RemoveRedundantWords(string inputText)
        {
            // Obtener las expresiones excluidas de la base de datos
            var excludedExpressions = _repository.GetAllExpressions();

            // Limpiar el texto de acuerdo con las expresiones excluidas
            return CleanText(inputText, excludedExpressions);
        }
    }
}
