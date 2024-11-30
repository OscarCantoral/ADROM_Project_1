using ProyADROMv1.DataAccess;
using ProyADROMv1.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ProyADROMv1
{
    class Program
    {
        static void Main(string[] args)
        {
            //Console.WriteLine("\n=== Ingrese un texto para limpiar ===");
            string inputText = Console.ReadLine();

            // Limpieza del texto
            string cleanedText = StringHelper.LipiezaTexto(inputText);

            // Separación del texto
            List<string> parts = StringHelper.SplitText(cleanedText);

            // Aplicar reglas de depuración
            List<string> cleanedParts = StringHelper.CleanArray(parts);

            // Obtener palabras clave de la base de datos
            var repository = new TpplbrRepository();
            List<string> keywords = repository.GetPalabrasBD().ConvertAll(e => e.Text.ToLower());

            // Buscar palabras clave en el texto partido
            var matches = FindMatchingRows(cleanedParts, keywords);

            //Console.WriteLine("\nFilas que contienen palabras clave:");
            //foreach (var match in matches)
            //{
            //    Console.WriteLine($"[Fila {match.Key}] {match.Value}");
            //}

            // Crear el arreglo final
            List<string> finalResult = new List<string>();

            if (matches.Count > 0)
            {
                int targetIndex = matches.Keys.Min(); // La primera fila que coincide
                string targetText = matches[targetIndex];

                //Console.WriteLine($"\nAnalizando filas después de la fila {targetIndex}:");
                for (int i = 0; i < cleanedParts.Count; i++)
                {
                    if (i <= targetIndex || CountWords(cleanedParts[i]) > 3)
                    {
                        // Filas no modificadas
                        finalResult.Add(cleanedParts[i]);
                    }
                    else
                    {
                        // Filas modificadas
                        string updatedText = $"{targetText} necesito {cleanedParts[i]}";
                        finalResult.Add(updatedText);
                    }
                }
            }
            else
            {
                // Si no hay palabras clave, incluir todas las filas como "sin cambios"
                for (int i = 0; i < cleanedParts.Count; i++)
                {
                    finalResult.Add(cleanedParts[i]);
                }
            }

            // Mostrar el resultado final
            //Console.WriteLine("\nResultado final:");
            foreach (var line in finalResult)
            {
                Console.WriteLine(line);
            }
        }

        static Dictionary<int, string> FindMatchingRows(List<string> textParts, List<string> keywords)
        {
            var matches = new Dictionary<int, string>();

            for (int i = 0; i < textParts.Count; i++)
            {
                string part = textParts[i].ToLower();
                foreach (var keyword in keywords)
                {
                    if (part.Contains(keyword))
                    {
                        matches[i] = textParts[i];
                        break; // Salir del bucle si se encuentra una coincidencia en esta fila
                    }
                }
            }

            return matches;
        }

        static int CountWords(string text)
        {
            if (string.IsNullOrWhiteSpace(text))
                return 0;

            return text.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries).Length;
        }
    }
}
