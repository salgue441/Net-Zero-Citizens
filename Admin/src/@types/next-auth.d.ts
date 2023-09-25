import { User } from '@/utils/authUtils'

declare module 'next-auth' {
  /**
   * Returned by `useSession`, `getSession` and received as a prop on the `SessionProvider` React Context
   */
  interface Session {
    user:? User
    authToken:? string
    refreshToken:? string
  } 
}

declare module 'next-auth/jwt' {
    /**
     * Returned by the `jwt` callback and `getToken`, when using JWT sessions
     */
    interface JWT {
        user:? User
        authToken:? string
        refreshToken:? string
    }
}