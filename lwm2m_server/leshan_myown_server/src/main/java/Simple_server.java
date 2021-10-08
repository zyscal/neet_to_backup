
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.*;
import org.eclipse.leshan.core.response.*;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Simple_server {
    public static boolean ready_to_update_firmware = false;
    public static String firmware_uri = "coap://192.168.24.129:5700/data/new_firmware";
//    public static String firmware_uri = "coap://59.110.213.240:5683/data/anjay-bc6";
    public static void main(String args[])
    {

        LeshanServerBuilder builder = new LeshanServerBuilder();
        NetworkConfig networkConfig = LeshanServerBuilder.createDefaultNetworkConfig();
        networkConfig.set(NetworkConfig.Keys.PROTOCOL_STAGE_THREAD_COUNT, 5);
        networkConfig.setInt(NetworkConfig.Keys.COAP_PORT, 5683);
        networkConfig.setInt(NetworkConfig.Keys.COAP_SECURE_PORT, 5684);
        System.out.println(networkConfig.getInt(NetworkConfig.Keys.COAP_PORT));
        networkConfig.setInt(NetworkConfig.Keys.NSTART, 5);
//        System.out.println(networkConfig.getInt(NetworkConfig.Keys. ));
        networkConfig.set(NetworkConfig.Keys.ACK_TIMEOUT, 6000);

        builder.setCoapConfig(networkConfig);
        InetSocketAddress localAddress = new InetSocketAddress(5683);
        builder.setLocalAddress("192.168.3.24", 5683);
        LeshanServer server = builder.build();
        System.out.println("ip : " + localAddress.getAddress().getHostAddress());

        server.getObservationService().addListener(new ObservationListener() {
            public Integer firmware_state_success = 2;
            public Integer firmware_state_downloading = 1;
            public Integer firmware_state_idle = 0;
            @Override
            public void newObservation(Observation observation, Registration registration) {
                System.out.println("newObservation");
            }
            @Override
            public void cancelled(Observation observation) {
                System.out.println("cancelled");
            }

            @Override
            public void onResponse(Observation observation, Registration registration, ObserveResponse observeResponse) {
                System.out.println("onResponse");
                System.out.println(observation.getPath().getObjectId());
                System.out.println(observation.getPath().getObjectInstanceId());
                System.out.println(observation.getPath().getResourceId());
                if(check_path(observation, 34828, 0,29272))
                {
                    Integer result = Integer.parseInt((String) ((LwM2mSingleResource)observeResponse.getContent()).getValue());
                    System.out.println("/34828/0/29272 observe : " + result);
                    if(result == 4)
                    {
                        System.out.println("end of change, go to next part");

                    }
                }
                else if(check_path(observation, 5,0 ,3))
                {
                    long result = (long) ((LwM2mSingleResource)observeResponse.getContent()).getValue();
                    System.out.println("/5/0/3 observe : " + result);

                    if(result == firmware_state_success)
                    {
                        System.out.println("I CAN UPDATE");
//                        try {
//                            ExecuteResponse executeResponse = server.send(registration, new ExecuteRequest(5,0,2));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

                    try {
                        ObserveResponse observeResponse2 = server.send(registration, new ObserveRequest(34828, 0, 29272));
                        if(observeResponse2.isSuccess())
                        {
                            System.out.println("observe 34828 success !");
                        }else{
                            System.out.println("observe 34828 failed !");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    }
                }





            }

            @Override
            public void onError(Observation observation, Registration registration, Exception e) {
                System.out.println("onError");
            }

            public boolean check_path(Observation observation, Integer objectId, Integer objectInstanceId, Integer resourceId){

                System.out.println(observation.getPath().getObjectId() +":" + objectId);
                System.out.println(observation.getPath().getObjectInstanceId() + ":" + objectInstanceId);
                System.out.println(observation.getPath().getResourceId() + ":" + resourceId);


                if(observation.getPath().getObjectId().equals(objectId)  &&
                observation.getPath().getObjectInstanceId().equals(objectInstanceId)  &&
                observation.getPath().getResourceId().equals(resourceId) )
                {
                    return true;
                }
                return false;
            }
        });
        server.getRegistrationService().addListener(new RegistrationListener() {
            public void registered(Registration registration, Registration previousReg,
                                   Collection<Observation> previousObsersations) {
                System.out.println("new device: " + registration.getEndpoint());
                Map<Integer, String> allObj = registration.getSupportedObject();
                Set<Integer> allObj_key = allObj.keySet();
                for(Integer i : allObj_key)
                {
                    System.out.println("trying : " + i);
                    try {
                        ReadResponse response = server.send(registration, new ReadRequest(i,0));
                        if (response.isSuccess()) {
                            System.out.println(((LwM2mObjectInstance)response.getContent()).getResources());
                        }else {
                            System.out.println("Failed to read:" + response.getCode() + " " + response.getErrorMessage());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                        try {
                            ObserveResponse observeResponse_f = server.send(registration, new ObserveRequest(5, 0, 3));
                            if(observeResponse_f.isSuccess())
                            {
                                System.out.println("observer /5/0/3 success !");
                            }
                            else
                            {
                                System.out.println("fail to observe /5/0/3");
                                return;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                System.out.println("before update");
                try {
                    WriteResponse writeResponse = server.send(registration, new WriteRequest(5,0, 1, Simple_server.firmware_uri));
                    if (writeResponse.isSuccess())
                    {
                        System.out.println("write URI success");
                    }
                    else{
                        System.out.println("writer URI failed!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }

            public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
                System.out.println("device is still here: " + updatedReg.getEndpoint());


            }

            public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
                                     Registration newReg) {
                System.out.println("device left: " + registration.getEndpoint());
            }

        });
        server.start();

        System.out.println("end of start");

    }
}

