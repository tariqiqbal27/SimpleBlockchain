import models.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Blockchain {

    private final List<Block> chain = new ArrayList<>();

    public Blockchain() {
        createBlock(1,"0");
        System.out.println("Genesis Block Created");
    }

    public Block createBlock(long proof, String previousHash) {
        var timestamp = new Date().getTime();
        var block = new Block(this.chain,timestamp,proof,previousHash);
        this.chain.add(block);
        return block;
    }

    public Block get_previous_block() {
        return this.chain.get(chain.size()-1);
    }

    private long proofOfWork(long previousHash) {
        int new_proof = 1;
        boolean checkProof = false;
        while(!checkProof) {
            long input = new_proof^2 - previousHash^2;
            String calc_hash = sha256(String.valueOf(input));
            // Increase No of 0 to increase difficulty
            if(calc_hash.startsWith("0000"))
                checkProof = true;
            else
                new_proof +=1;
        }
        return new_proof;
    }

    public String hash(Block block) {
        var encoded = block.toString();
        return sha256(encoded);
    }

    public boolean isChainValid(List<Block> localChain) {
        var previousBlock = localChain.get(0);
        int blockIndex = 1;
        while(blockIndex < localChain.size()) {
            var block = localChain.get(blockIndex);
            if(!Objects.equals(block.getPreviousHash(), this.hash(previousBlock)))
                return false;
            var previousHash = previousBlock.getProof();
            var proof = block.getProof();
            long input = proof^2 - previousHash^2;
            String calc_hash = sha256(String.valueOf(input));
            if(!calc_hash.startsWith("0000"))
                return false;
            previousBlock = block;
            blockIndex += 1;
        }
        return true;
    }

    public static String sha256(String input) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            int i = 0;
            byte[] hash = sha.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexHash = new StringBuilder();
            while (i < hash.length) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexHash.append('0');
                hexHash.append(hex);
                i++;
            }
            return hexHash.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Block> getChain() {
        return chain;
    }

    public void mineBlock() {
        var previousBlock = this.get_previous_block();
        var previousProof = previousBlock.getProof();
        var proof = this.proofOfWork(previousProof);
        var previousHash = this.hash(previousBlock);
        var block = this.createBlock(proof,previousHash);
        System.out.println("Block Mined Successfully, Index: " + block.getIndex());
    }

}
