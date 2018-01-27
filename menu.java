import java.lang.*;
import java.io.*;

class menu {

	private static final String TITLE =  "\n2910326 Computer Security coursework\n" + 
	"\t1. Step-By-Step: Generating a public key \n" +
	"\t2. Step-By-Step: Generating a private key \n" +
	"\t3. Step-By-Step: Encrypting and Decrypting \n" +
	"\t4. Communication Scenario \n" +
	"\t0. Exit \n" +
	"\t********************\n" + 
	"Please input a single digit (0-4):\n";

	menu() {
		int selected = -1;
		while (selected != 0) {
			System.out.println(TITLE);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			try {
				selected = Integer.parseInt(input.readLine());

				switch (selected) {
					case 1: q1();
					break;
					case 2: q2();
					break;
					case 3: q3();
					break;
					case 4: q4();
				}
			} catch (Exception ex) {}
		} 
		System.out.println("Bye!");
	}

	private void q1() {
		RSA pKey = new RSA();
		System.out.println("1. Generate two primes (p and q)");
		System.out.println("- They must be distinct");
		System.out.println("- They are chosen at random");
		System.out.println("- Can be checked whether it is a prime using a primality test");
		System.out.println("Generated Prime P: " + pKey.getPrimeP());
		System.out.println("Generated Prime Q: " + pKey.getPrimeQ());

		System.out.println("\n" + "2. Compute n");
		System.out.println("Computation: n = p*q ");
		System.out.println("n will be used as one part of the public key");
		System.out.println("Computed n: " + pKey.getN());

		System.out.println("\n" + "3. Compute the totient");
		System.out.println("- Computation: totient = (p-1)*(q-1)");
		System.out.println("Totient: " + pKey.getTot());

		System.out.println("\n" + "4. Compute e");
		System.out.println("- e will be used as the second part of the public key");
		System.out.println("- Must be a number between one and the totient that is cooprime of the totient");
		System.out.println("Computed e: " + pKey.getE());

		System.out.println("\n" + "5. Now the public key has been generated!");
		System.out.println("- n and e values are used as a public key");
		System.out.println("Computed public key: " + "(n: " + pKey.getN() + ", e: " + pKey.getE() + ")");
	}

	private void q2() {
		RSA pKey = new RSA();

		System.out.println("1. Compute a public key");
		System.out.println("- Public key consists of two values: (e, n)");
		System.out.println("Computed public key: " + "(n: " + pKey.getN() + ", e: " + pKey.getE() + ")");

		System.out.println("\n" + "2. Compute d");
		System.out.println("- Compute a value for d so that d*e mod totient = 1");
		System.out.println("- d must be greater than 1 and less than the totient");
		System.out.println("- d must be cooprime to totient");
		System.out.println("- d must not be a divisor of the totient");
		System.out.println("Computed d: " + pKey.getD());

		System.out.println("\n" + "3. Now the private key has been generated!");
		System.out.println("- d value is used as a private key");
		System.out.println("- This value is kept secret");

		System.out.println("\n" + "4. Test it out!");
		System.out.println("- Is d calculated correctly so that d * e mod totient = 1?");
		System.out.println((pKey.getD().multiply(pKey.getE())).mod(pKey.getTot()));
	}

	private void q3() {
		RSA pKey = new RSA();

		String message = "Hello Bob.";
		System.out.println("\n" + "1. Get a message to encrypt");
		System.out.println("- Must be an integer");
		System.out.println("- If the message is a string, convert it into a sequence of bytes");
		System.out.println("Example message: " + message);

		System.out.println("\n" + "2. Generate a public key");
		System.out.println("- Need to have a generated public key to encrypt");
		System.out.println("Computed public key: " + "(n: " + pKey.getN() + ", e: " + pKey.getE() + ")");

		System.out.println("\n" + "3. Turn the message into a cipher using the public key");
		System.out.println("- Computation: m^e mod n");
		
		String cipher = pKey.encryptMessage(message);
		System.out.println("Cipher is: " + cipher);

		System.out.println("\n" + "4. Decrypt the message using the private key");
		System.out.println("- Computation: c^d mod n");
		String plaintext = pKey.decryptMessage(cipher);
		System.out.println("Decrypted message: " + plaintext);
	}

	private void q4() {
		RSA bob = new RSA();

		System.out.println("\n" + "1. Bob generates a public and a private key");
		System.out.println("- Public key is public and private key is to be kept private");
		System.out.println("Generated public key: " + "(n: " + bob.getN() + " e: " + bob.getE() + ")");
		System.out.println(" Generated private key: " + bob.getD());

		System.out.println("\n" + "2. Bob publishes his public key");

		System.out.println("\n" + "3. Alice fetches the public key");
		System.out.println("- At this point, Charlie might intercept the public key");

		String message = "Hello Bob.";
		System.out.println("\n" + "4. Alice encrypts the message using Bob's public key");
		System.out.println("Example message: " + message);
		String cipher = bob.encryptMessage(message);
		System.out.println("Generated cipher: " + cipher);

		System.out.println("\n" + "5. Alice sends the encrypted cipher to Bob");
		System.out.println("- At this point, Charlie might intercept the cipher");

		System.out.println("\n" + "6. Bob decrypts the cipher Alice sent using his private key");
		System.out.println("- The private key was never transferred");
		System.out.println("- If Bob wants to reply, he has to fetch Alice's public key too");
		String plaintext = bob.decryptMessage(cipher);
		System.out.println("Decrypted message: " + plaintext);

		System.out.println("\n" + "What was Charlie able to intercept?");
		System.out.println("Charlie was able to intercept the public key of Bob: " + "(n: " + bob.getN() + " e: " + bob.getE() + ")");
		System.out.println("Charlie was able to intercept the generated cipher: " + cipher);
	}

	public static void main(String[] args) {
		new menu();
	}
}