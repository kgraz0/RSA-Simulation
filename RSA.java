import java.math.BigInteger;
import java.util.Random;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

class RSA {
	BigInteger primeP, primeQ, n, totient, e, d;

	public RSA() {
		primeP = generatePrime(64); // generate a prime of bit length 64
		primeQ = generatePrime(64); // generate a prime of bit length 64
		n = generateN(primeP, primeQ); // generate n value
		totient = generateTot(primeP, primeQ); // generate the totient value (phi)
		e = generateE(totient.subtract(ONE), totient); // generate e value
		d = generatePrivateKey(e, totient); // generate d value (private key)
	}

	public BigInteger generatePrime(int bitLength) {
		// generate a random probable prime number of given bit length
		BigInteger prime = BigInteger.probablePrime(bitLength, new Random());
		return prime;
	}

	public BigInteger generateN(BigInteger prime1, BigInteger prime2) {
		 // generate n by multiplying both primes together
		 return (prime1.multiply(prime2));
	}

	public BigInteger generateTot(BigInteger prime1, BigInteger prime2) {
		// generate the totient by taking away 1 from each prime then multiplying
		return prime1.subtract(ONE).multiply((prime2.subtract(ONE)));
	}
	
	public static BigInteger generatePrimeRange(BigInteger min, BigInteger max) {
		BigInteger genNum;

		do {
			// generate a probable prime while the generated value is greater than min and smaller than max
			genNum = BigInteger.probablePrime(max.bitLength(), new Random());
		} while (genNum.compareTo(min) < 0 || genNum.compareTo(max) > 0);

		return genNum;
		}
	

	public BigInteger generateE(BigInteger max, BigInteger tot) {
		BigInteger e = generatePrimeRange(ONE, max);

		// check whether e's greatest common divisor of totient value does not equal to one
		while (!e.gcd(tot).equals(ONE)) {
			e = BigInteger.probablePrime(tot.bitLength(), new Random()); // generate a probable prime for e
		}

		return e;
	}

	// generate the private key by using the extended euclidean algorithm
	public static BigInteger generatePrivateKey(BigInteger e, BigInteger totient) {
        // create a 2D array
        BigInteger eea[][] = new BigInteger[2][2];

        /*
        // store totient value at the top row in both columns
        // store value of e in the first column second row
        // store value of 1 in the second column second row
        */ 
        eea[0][0] = totient;
        eea[0][1] = e; 

        eea[1][0] = totient;
        eea[1][1] = ONE;

        // while loop is active whilst position at 0, 1 does not equal to one
        while (!eea[0][1].equals(ONE)) {
        	// divide totient by e value
            BigInteger multiplier = eea[0][0].divide(eea[0][1]);
            // sutract totient from e (or one) then multiply by previous value for both columns
            BigInteger col1Answer = eea[0][0].subtract(eea[0][1].multiply(multiplier));
            BigInteger col2Answer = eea[1][0].subtract(eea[1][1].multiply(multiplier));

            // if the calculated answer is a negative value, update it with mod totient so the value is positive
            if (col1Answer.compareTo(ZERO) == -1) {
                col1Answer = col1Answer.mod(totient);
            } else if (col2Answer.compareTo(ZERO) == -1) {
                col2Answer = col2Answer.mod(totient);
            }

            // update the array values
            eea[0][0] = eea[0][1];
            eea[1][0] = eea[1][1];

            eea[0][1] = col1Answer;
            eea[1][1] = col2Answer;
        }

        return eea[1][1]; // return the d value where it equals to 1
    }

	public String encryptMessage(String message) {
		BigInteger byteInt = new BigInteger(message.getBytes()); // encode the string into sequence of bytes
		byteInt = byteInt.modPow(e, n); // update the value by applying encryption formula (m^e mod n)
		return byteInt.toString(); // return the sequence of bytes as string
	}

	public String decryptMessage(String message) {
		// apply the decryption formula (c^d mod n) to the cipher message and store as byte array
		byte[] decryptedMessage = (new BigInteger(message)).modPow(d, n).toByteArray();
		return new String(decryptedMessage); // return the byte array as String
	}

	// setters and getters start here
	public void setPrimeP(BigInteger prime) {
		this.primeP = prime;
	}

	public void setPrimeQ(BigInteger prime) {
		this.primeQ = prime;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public void setTot(BigInteger totient) {
		this.totient = totient;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public BigInteger getPrimeP() {
		return primeP;
	}

	public BigInteger getPrimeQ() {
		return primeQ;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getTot() {
		return totient;
	}

	public BigInteger getE() {
		return e;
	}

	public BigInteger getD() {
		return d;
	}
}