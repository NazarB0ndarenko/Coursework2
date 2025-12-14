import { useNavigate } from "react-router-dom"; // Add this import
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { useAuthContext } from "../../context/AuthContext";
import toast from "react-hot-toast";

function GoogleProvider() {
    const { updateJwt } = useAuthContext();
    const navigate = useNavigate(); // Add this line

    const handleSuccess = async (response) => {
        const oauthToken = response.credential;

        try {
            const res = await fetch(
                `${process.env.REACT_APP_BACKEND_DOMAIN}/login/oauth2/code/google`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ oauthToken }),
                }
            );

            const data = await res.json();
            console.log("Response from backend:", data);

            if (data.jwt) {
                updateJwt(data.jwt);
                console.log("JWT updated successfully");
                toast.success("Login successful!");

                setTimeout(() => {
                    navigate("/dashboard");
                }, 100);
            } else {
                console.error("No JWT in response");
                handleFailure();
            }
        } catch (error) {
            console.error("Login error:", error);
            handleFailure();
        }
    };

    const handleFailure = (error) => {
        toast.error("Service is currently unavailable!");
    };

    return (
        <GoogleOAuthProvider clientId="549368762792-jqkl67gfi9c69fr7dh402tj9cjvlh2u4.apps.googleusercontent.com">
            <GoogleLogin onSuccess={handleSuccess} onError={handleFailure} />
        </GoogleOAuthProvider>
    );
}

export default GoogleProvider;