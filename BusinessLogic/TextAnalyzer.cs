using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace ProyADROMv1.BusinessLogic
{
    public class TextAnalyzer
    {
        // Método para separar el texto en frases utilizando delimitadores.
        public List<string> SplitText(string input)
        {
            // Separar el texto por punto, coma o "y" como delimitadores
            var splitPattern = @"(?<=\w)[.,\s]+|\s+\b(y)\b"; // Separadores: punto, coma, y
            var phrases = Regex.Split(input, splitPattern)
                                .Where(x => !string.IsNullOrWhiteSpace(x))  // Eliminar frases vacías
                                .ToList();
            return phrases;
        }

        // Método para eliminar los saludos usando una lista simple de palabras clave de saludos
        public List<string> RemoveGreetings(List<string> phrases)
        {
            var greetings = new List<string> { "hola", "buenos días", "buenas tardes", "buenas noches", "que tal", "hello" };
            return phrases.Where(phrase => !greetings.Any(greeting => phrase.ToLower().Contains(greeting))).ToList();
        }

        // Método para detectar las palabras clave en el texto (puede ser ajustado según lo que busques)
        public List<string> ExtractKeywords(List<string> phrases)
        {
            var keywords = new List<string> { "trabajador", "asistencia", "consumo", "reportes", "data", "tickets", "llamadas" };
            var keywordPhrases = phrases.Where(phrase => keywords.Any(keyword => phrase.ToLower().Contains(keyword))).ToList();
            return keywordPhrases;
        }

        // Método para organizar el texto en un formato de JSON
        public List<object> CreateJson(List<string> phrases, List<string> keywords)
        {
            var groupedText = new List<object>();

            foreach (var phrase in phrases)
            {
                var phraseKeywords = keywords.Where(keyword => phrase.ToLower().Contains(keyword)).ToList();
                groupedText.Add(new
                {
                    Text = phrase,
                    Keywords = phraseKeywords
                });
            }

            return groupedText;
        }
    }
}