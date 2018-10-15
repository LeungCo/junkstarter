package com.docker.junkstarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "devtools")
public class YAMLDevToolsLConfig {

	private final Remote remote = new Remote();
	private final Restart restart = new Restart();
	private final Livereload livereload = new Livereload();

	public static class Remote {
		private String secret;

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}
	}

	public static class Restart {
		private String enabled;

		public String getEnabled() {
			return enabled;
		}

		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}
	}

	public static class Livereload {
		private String enabled;

		public String getEnabled() {
			return enabled;
		}

		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}
	}

	public Remote getRemote() {
		return remote;
	}

	public Restart getRestart() {
		return restart;
	}

	public Livereload getLivereload() {
		return livereload;
	}

}
