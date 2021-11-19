package models;

import java.util.List;

public class Block {

    private long index;
    private long timestamp;
    private long proof;
    private String previousHash;

    public Block(List<Block> chain,long timestamp, long proof, String previousHash){
        this.index = chain.size() + 1;
        this.proof = proof;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    @Override
    public String toString() {
        return String.format(
                "\n{ Index: %d Timestamp: %d Proof: %d Previous_Hash: %s }",
                index, timestamp, proof,previousHash
        );

    }
}
