using System.Net.Sockets;
using System.Text;
using System.Text.Json.Serialization;
using Newtonsoft.Json;
using RtspClientSharp;
using RtspClientSharp.Rtsp;

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

            //while (true)
            //{


            try
            {

                Console.WriteLine("Establishing connection...");

                var list = new List<object>();

                using (var streamReader = new StreamReader(await client.GetStreamAsync(url)))
                {
                    var num = 0;

                    while (!streamReader.EndOfStream)
                    {
                        if (num > 1000)
                            break;

                        var message = await streamReader.ReadLineAsync();

                        if (message is not null && message.Contains("data:"))
                        {
                            list.Add(message);
                            num++;
                        }
                        //streamReader.
                        
                        //var deserMessage = JsonConvert.DeserializeObject(message);
                        //    Console.WriteLine(message);
                        //    list.Add(message);
                    }

                    Console.WriteLine("There are: " + list.Count + " RECORDS");
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