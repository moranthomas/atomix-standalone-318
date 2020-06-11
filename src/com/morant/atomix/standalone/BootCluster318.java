package com.morant.atomix.standalone;

import io.atomix.core.Atomix;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;
import io.atomix.protocols.raft.partition.RaftPartitionGroupConfig;
import io.atomix.protocols.raft.partition.RaftStorageConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

import static java.lang.Thread.*;


public class BootCluster318 {

		private static final String CONF_PATH = "./conf/test-cluster.conf";
		private static final Logger LOG = LoggerFactory.getLogger(BootCluster318.class);
		
		public static void main(String[] args) {
			
			try {
				atomix(1);
				atomix(2);
				atomix(3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private static void atomix(int id) throws Exception {
		    
			Atomix.builder(CONF_PATH)
		          .withManagementGroup(new RaftPartitionGroup(
		              new RaftPartitionGroupConfig().setName("atomix")
		                                            .setPartitions(1)
		                                            .setMembers(new HashSet<>(Arrays.asList(
		                                                "atomix-1", "atomix-2", "atomix-3"
		                                            )))
		                                            .setStorageConfig(new RaftStorageConfig().setDirectory(
		                                                "target/atomix-" + id + "/system"
		                                            ))
		          ))
		          .withPartitionGroups(new RaftPartitionGroup(
		              new RaftPartitionGroupConfig().setName("data")
		                                            .setPartitions(7)
		                                            .setPartitionSize(3)
		                                            .setMembers(new HashSet<>(Arrays.asList(
		                                                "atomix-1", "atomix-2", "atomix-3"
		                                            )))
		                                            .setStorageConfig(new RaftStorageConfig().setDirectory(
		                                                "target/atomix-" + id + "/data"
		                                            ))
		          ))
		          .withMemberId("atomix-" + id)
		          .withHost("localhost")
		          .withPort(5550 + id)
		          .build()
		          .start()
		          .join();        // Never get here 
			
		    LOG.info("Atomix Node {} started", id);
		    
		    while (true) {
		        sleep(1000);
		    }
		}

}