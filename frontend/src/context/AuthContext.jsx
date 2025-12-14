import { createContext, useContext, useState, useEffect, useCallback } from "react"; // Add useCallback
import axios from "axios";
import useLocalStorage from "../hooks/useLocalStorage";
import Spinner from "../ui/Spinner";

const AuthContext = createContext({
  user: null,
  logout: () => {},
  updateJwt: (jwt) => {},
  refreshUser: () => {},
  jwt: "",
  trigger: true,
});

const AuthProvider = ({ children }) => {
  const {
    state: jwt,
    updateLocalStorage: updateJwt,
    deleteFromLocalStorage: logout,
  } = useLocalStorage("jwt", "");

  const [user, setUser] = useState(null);
  const [premiumAccount, SetPremiumAccount] = useState(null);
  const [isReady, setIsReady] = useState(false);
  const [trigger, setTrigger] = useState(true);

  function refreshUser() {
    setTrigger(!trigger);
  }

  // Wrap identifyJwt in useCallback
  const identifyJwt = useCallback(async () => {
    const API_ENDPOINT = `${process.env.REACT_APP_API_URL}/identity`;
    console.log("🔍 Calling identifyJwt with JWT:", jwt ? "exists" : "missing");

    if (!jwt) {
      console.log("❌ No JWT, skipping identity check");
      setIsReady(true);
      return;
    }

    try {
      console.log("📡 Calling:", API_ENDPOINT);
      const response = await axios.get(API_ENDPOINT, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      console.log("✅ Identity response:", response.data);

      setUser(response.data); // Fixed: backend returns user directly
      SetPremiumAccount(null); // Premium removed

    } catch (e) {
      console.error("❌ identifyJwt error:", e.response?.status, e.response?.data);
      setUser(null);
    }
    setIsReady(true);
  }, [jwt]); // Add jwt as dependency

  useEffect(() => {
    identifyJwt();
  }, [jwt, trigger, identifyJwt]); // Now includes identifyJwt

  return (
      <AuthContext.Provider
          value={{
            user,
            premiumAccount,
            updateJwt,
            logout,
            refreshUser,
            jwt,
            trigger,
          }}
      >
        {!isReady ? <Spinner /> : children}
      </AuthContext.Provider>
  );
};

function useAuthContext() {
  const context = useContext(AuthContext);
  if (context === undefined)
    throw new Error("AuthContext must be used within a AuthProvider");

  return context;
}

export { AuthProvider, useAuthContext };
