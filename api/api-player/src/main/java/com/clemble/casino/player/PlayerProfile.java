package com.clemble.casino.player;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Version;

import org.springframework.data.annotation.Id;
import org.springframework.social.connect.ConnectionKey;

import com.clemble.casino.VersionAware;
import com.clemble.casino.error.ClembleCasinoError.Code;
import com.clemble.casino.error.validation.MaxSize;
import com.clemble.casino.error.validation.NickNameConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerProfile implements PlayerAware, VersionAware {

    /**
     * Generated 03/11/13
     */
    private static final long serialVersionUID = -3831720055626662655L;

    @Id
    @JsonProperty(PlayerAware.JSON_ID)
    private String player;

    @JsonProperty("nickName")
    @NickNameConstraint(message = Code.NICK_INVALID_CODE)
    @MaxSize(max = 64, message = Code.NICK_TOO_LONG_CODE)
    private String nickName;

    @JsonProperty("firstName")
    @MaxSize(max = 64, message = Code.FIRST_NAME_TOO_LONG_CODE)
    private String firstName;

    @JsonProperty("lastName")
    @MaxSize(max = 64, message = Code.LAST_NAME_TOO_LONG_CODE)
    private String lastName;

    @JsonProperty("gender")
    private PlayerGender gender;

    @JsonProperty("birthDate")
    private Date birthDate;

    private Set<ConnectionKey> socialConnections = new HashSet<ConnectionKey>();

    @Version
    @JsonProperty("version")
    private int version;

    public PlayerProfile() {
    }

    public String getPlayer() {
        return player;
    }

    public PlayerProfile setPlayer(String player) {
        this.player = player;
        return this;
    }

    public Collection<ConnectionKey> getSocialConnections() {
        return socialConnections;
    }
    
    public ConnectionKey getSocialConnection(String socialNetwork) {
        // Step 1. Sanity check
        if(socialNetwork == null)
            return null;
        // Step 2. Filtering social connections
        for(ConnectionKey key: socialConnections)
            if(key.getProviderId().equals(socialNetwork))
                return key;
        // Step 3. No connection found returning null
        return null;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public PlayerProfile setVersion(int version) {
        this.version = version;
        return this;
    }

    public PlayerProfile setSocialConnections(Set<ConnectionKey> socialConnections) {
        this.socialConnections = socialConnections;
        return this;
    }

    public PlayerProfile addSocialConnection(ConnectionKey newConnectionKey) {
        socialConnections.add(newConnectionKey);
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public PlayerProfile setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PlayerGender getGender() {
        return gender;
    }

    public PlayerProfile setGender(PlayerGender gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public PlayerProfile setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + ((socialConnections == null) ? 0 : socialConnections.hashCode());
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerProfile other = (PlayerProfile) obj;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (gender != other.gender)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (nickName == null) {
            if (other.nickName != null)
                return false;
        } else if (!nickName.equals(other.nickName))
            return false;
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        if (socialConnections == null) {
            if (other.socialConnections != null)
                return false;
        } else if (!socialConnections.equals(other.socialConnections))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

}