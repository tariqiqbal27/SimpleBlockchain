public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.mineBlock();

        System.out.println(blockchain.getChain());
        System.out.println("Is Chain Valid: "+ blockchain.isChainValid(blockchain.getChain()));
    }
}
