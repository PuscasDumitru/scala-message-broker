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
            var tweetsUrl = "http://localhost:8080/tweets/1";

            var clientToGetTweets = new HttpClient();
            TcpClient clientForBroker;

            BinaryWriter writer;
            BinaryReader reader;
            NetworkStream stream;

            try
            {
                Console.WriteLine("Establishing connection with tweets stream...");

                using (var streamReader = new StreamReader(await clientToGetTweets.GetStreamAsync(tweetsUrl)))
                {
                    while (!streamReader.EndOfStream)
                    {
                        var message = await streamReader.ReadLineAsync();

                        if (message is not null && message.Contains("data:"))
                        {
                            var input = Console.ReadLine();
                            var splitCommands = input.Split(" ");
                            
                            var serializedMessage = JsonConvert.SerializeObject(
                                new
                                {
                                    topic = splitCommands[1],
                                    message
                                });

                            using (clientForBroker = new TcpClient("127.0.0.1", 8081))
                            {
                                Console.WriteLine("Connection with broker established...");
                                using (stream = clientForBroker.GetStream())
                                {
                                    Console.WriteLine("Stream retrieved from the connection...");

                                    //string received = null;
                                    writer = new BinaryWriter(stream);
                                    writer.Write(serializedMessage);
                                    Console.WriteLine("Message sent successfully...\n\n");
                                    //reader = new BinaryReader(stream);
                                    //received = reader.ReadString();
                                    //Console.WriteLine(received + " was received...");
                                }
                            }

                        }

                    }
                }
                
            }
            catch (Exception e)
            {
                Console.WriteLine("There was an error: " + e.Message);
            }

        }
    }
}