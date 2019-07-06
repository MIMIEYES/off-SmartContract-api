package io.nuls.offlinecontract.test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import io.nuls.contract.model.RpcResult;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;


public class RpcContractClientTest {
    private static JsonRpcHttpClient memberClient;
    private int chainId=2;
    private  int assetId=1;

    private String sender="tNULSeBaMvEtDfvZuukDf2mVyfGo3DdiN8KLRG";

    private String contractAddress_nrc = "tNULSeBaNCHAhqG84z2kdeHx6AuFH6Zk6TmDDG";

    //jar测试使用的合约地址
    //private String contractAddress="tNULSeBaN15fihAZMefPMNYJyPuvYHBA1iEuka";

    //IDEA环境测试使用的合约地址
    private String contractAddress="tNULSeBaN2DRmmBXYfD8AbqSzqsGVDZrsdgwYj";

    private String password="nuls123456";
    private String alias="contractAlias";
    private long gasLimit=100000;

    private long price=25;
    private  String remark="test";
    @Before
    public void setUp() throws Throwable {
        memberClient = new JsonRpcHttpClient(new URL("http://127.0.0.1/contract"));
    }

    @Test
    public void createContract()throws Throwable {
        String contractCode=getContractCode("D:\\BlockChain-nuls\\Pocm-contract-beta\\pocmContract-version-beta1\\target\\pocmContract-test2.jar");
        Object[] args = new Object[]{contractAddress_nrc,12000, 2, 0.5, 2, false, "tNULSeBaMtEPLXxUgyfnBt9bpb5Xv84dyJV98p", "", ""};
        RpcResult result=memberClient.invoke("createContract",new Object[]{chainId,assetId,sender,password,contractCode,alias,args,gasLimit,price,remark},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void getContractConstructor()throws Throwable {
        String contractCode=getContractCode("D:\\BlockChain-nuls\\Pocm-contract-beta\\pocmContract-version-beta1\\target\\pocmContract-test2.jar");
        BigInteger value= BigInteger.ZERO;
        Object[] args=new Object[]{};
        RpcResult result=memberClient.invoke("getContractConstructor",new Object[]{chainId,contractCode},RpcResult.class);
        System.out.println(result);
    }
    @Test
    public void getContract()throws Throwable {
        RpcResult result=memberClient.invoke("getContract",new Object[]{chainId,contractAddress},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void callContractMethodNoArgs()throws Throwable {
        String methodName="depositForOwn";
        BigInteger value= BigInteger.valueOf(50000000);
        Object[] args=new Object[]{};
        RpcResult result=memberClient.invoke("callContract",new Object[]{chainId,assetId,sender,password,contractAddress,value,
                methodName,"",args,gasLimit,price,remark},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void invokeContractViewMethod()throws Throwable {
        String methodName="totalDepositDetail";
        BigInteger value= BigInteger.ZERO;
        Object[] args=new Object[]{};
        RpcResult result=memberClient.invoke("invokeContractViewMethod",new Object[]{chainId,contractAddress,methodName,"",args},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void getContractMethodArgsTypes()throws Throwable {
      //  String methodName="totalDepositDetail";
        String methodName="depositForOther";
        RpcResult result=memberClient.invoke("getContractMethodArgsTypes",new Object[]{chainId,contractAddress,methodName},RpcResult.class);
        System.out.println(result);
    }

    @Test
    public void callContractMethodHavingArgs()throws Throwable {
        String methodName="quit";
        BigInteger value= BigInteger.ZERO;
        Object[] args=new Object[]{"1"};
        RpcResult result=memberClient.invoke("callContract",new Object[]{chainId,assetId,sender,password,contractAddress,value,
                methodName,"",args,gasLimit,price,remark},RpcResult.class);
        System.out.println(result);
    }
    @Test
    public void deleteContract()throws Throwable {
        RpcResult result=memberClient.invoke("deleteContract",new Object[]{chainId,assetId,sender,password,contractAddress,remark},RpcResult.class);
        System.out.println(result);
    }

    private String getContractCode(String sourcePath){
        String hexEncode="";
        try {
            InputStream jarFile = new FileInputStream(sourcePath);
            byte[] contractCode= IOUtils.toByteArray(jarFile);
            hexEncode= Hex.encodeHexString(contractCode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return hexEncode;
    }

}
