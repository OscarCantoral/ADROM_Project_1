using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using System.Text.RegularExpressions;
using ProyADROMv1.BusinessLogic;

namespace ProyADROMv1.Helpers
{
    public static class StringHelper
    {
        public static string RemoveRedundantSpaces(string input)
        {
            while (input.Contains("  "))
            {
                input = input.Replace("  ", " ");
            }
            return input.Trim();
        }
        // Método para quitar tildes de una cadena de texto
        public static string RemoveAccents(string input)
        {
            if (string.IsNullOrEmpty(input))
                return input;

            // Normaliza la cadena a forma de descomposición
            var normalizedString = input.Normalize(NormalizationForm.FormD);

            var stringBuilder = new StringBuilder();

            foreach (var c in normalizedString)
            {
                // Agrega solo los caracteres que no son diacríticos
                if (CharUnicodeInfo.GetUnicodeCategory(c) != UnicodeCategory.NonSpacingMark)
                    stringBuilder.Append(c);
            }

            // Normaliza nuevamente a la forma compuesta
            return stringBuilder.ToString().Normalize(NormalizationForm.FormC);
        }

        // Método para limpiar comas y puntos adicionales
        public static string CleanPunctuation(string input)
        {
            // Eliminar comas o puntos seguidos de espacio
            input = input.Replace(", ", ",");
            input = input.Replace(". ", ".");
            input = input.Replace(" ,", ",");
            input = input.Replace(" .", ".");

            // Eliminar comas o puntos al principio o final
            input = input.TrimStart(',', '.');
            input = input.TrimEnd(',', '.');

            input = input.Replace(",,", ",");
            input = input.Replace("..", ".");
            input = input.Replace(".,", ".");
            input = input.Replace(",.", ".");

            return input;
        }

        public static string LipiezaTexto(string input)
        {
            var service = new ExpressionService();
            input = service.RemoveRedundantWords(input);
            input = RemoveAccents(input);
            input = CleanPunctuation(input);
            input = RemoveRedundantSpaces(input);
            input = CleanPunctuation(input);
            return input;
        }

        public static List<string> SplitText(string input)
        {
            if (string.IsNullOrWhiteSpace(input))
                return new List<string>();

            // Dividir el texto según los delimitadores
            string[] delimiters = { ",", ".", " y " };
            var parts = new List<string>();
            int start = 0;

            while (start < input.Length)
            {
                int minIndex = -1;

                foreach (var delimiter in delimiters)
                {
                    int index = input.IndexOf(delimiter, start);
                    if (index != -1 && (minIndex == -1 || index < minIndex))
                    {
                        minIndex = index;
                    }
                }

                if (minIndex == -1)
                {
                    parts.Add(input[start..].Trim());
                    break;
                }

                string segment = input[start..minIndex].Trim();
                if (!string.IsNullOrEmpty(segment))
                {
                    parts.Add(segment);
                }

                start = minIndex + 1;
                if (input.Substring(minIndex).StartsWith(" y "))
                {
                    start += 2; // Mover 2 caracteres extra para " y "
                }
            }

            return parts;
        }

        public static List<string> CleanArray(List<string> input)
        {
            if (input == null || input.Count == 0)
                return input;

            // Regla 1: Eliminar la primera entrada si es menor a 12 caracteres
            if (input[0].Length < 12)
            {
                input.RemoveAt(0);
            }

            //// Regla 2: Eliminar contenido dentro de paréntesis
            //for (int i = 0; i < input.Count; i++)
            //{
            //    input[i] = RemoveParenthesesContent(input[i]);
            //}

            return input;
        }

    }
}
