using System.Net.Sockets;
using System.Text;
using System.Text.Json.Serialization;
using Newtonsoft.Json;

namespace Publisher
{
    public class Program
    {
        static async Task Main(string[] args)
        {
            var client = new HttpClient();

            var url = "http://localhost:8080/tweets/1";

            //var payload = new StringContent("something", Encoding.UTF8, "application/json");
            //var result = client.PostAsync(endpoint, payload).Result;

            //TcpClient client;
            //BinaryWriter writer;
            //BinaryReader reader;
            //NetworkStream stream;

            while (true)
            {
                try
                {
                    Console.WriteLine("Establishing connection...");
                    using (var streamReader = new StreamReader(await client.GetStreamAsync(url)))
                    {
                        while (!streamReader.EndOfStream)
                        {
                            var message = await streamReader.ReadLineAsync();
                            //var deserMessage = JsonConvert.DeserializeObject(message);
                            Console.WriteLine(message);
                        }
                    }

                    //using (client = new TcpClient("127.0.0.1", 8080))
                    //{
                    //    Console.WriteLine("Connection established...");
                    //    using (stream = client.GetStream())
                    //    {
                    //        Console.WriteLine("Stream retrieved from the connection...");
                    //        var sent1 = new 
                    //        {
                    //            Name = "name",
                    //            Id = "id"
                    //        };

                    //        var sent = JsonConvert.SerializeObject(sent1);

                    //        string received = null;
                    //        writer = new BinaryWriter(stream);
                    //        writer.Write(sent);
                    //        Console.WriteLine(sent + " was sent...");
                    //        reader = new BinaryReader(stream);
                    //        received = reader.ReadString();
                    //        Console.WriteLine(received + " was received...");
                    //    }
                    //}
                }
                catch (Exception e)
                {
                    Console.WriteLine("There was an error: " + e.Message);
                }
            }
            
        }
    }
}