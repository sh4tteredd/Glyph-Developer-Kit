package com.nothing.thirdparty;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGlyphService extends IInterface {
    public static final String DESCRIPTOR = "com.nothing.thirdparty.IGlyphService";

    void setFrameColors(int[] paramArrayOfint) throws RemoteException;

    void openSession() throws RemoteException;

    void closeSession() throws RemoteException;

    boolean register(String paramString) throws RemoteException;

    boolean registerSDK(String paramString1, String paramString2) throws RemoteException;

    public static class Default implements IGlyphService {
        public void setFrameColors(int[] colors) throws RemoteException {}

        public void openSession() throws RemoteException {}

        public void closeSession() throws RemoteException {}

        public boolean register(String key) throws RemoteException {
            return false;
        }

        public boolean registerSDK(String key, String device) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGlyphService {
        static final int TRANSACTION_setFrameColors = 1;

        static final int TRANSACTION_openSession = 2;

        static final int TRANSACTION_closeSession = 3;

        static final int TRANSACTION_register = 4;

        static final int TRANSACTION_registerSDK = 5;

        public Stub() {
            attachInterface(this, "com.nothing.thirdparty.IGlyphService");
        }

        public static IGlyphService asInterface(IBinder obj) {
            if (obj == null)
                return null;
            IInterface iin = obj.queryLocalInterface("com.nothing.thirdparty.IGlyphService");
            if (iin != null && iin instanceof IGlyphService)
                return (IGlyphService)iin;
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return (IBinder)this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int[] arrayOfInt;
            String _arg0;
            boolean _result;
            String _arg1;
            boolean bool1;
            String descriptor = "com.nothing.thirdparty.IGlyphService";
            if (code >= 1 && code <= 16777215)
                data.enforceInterface(descriptor);
            switch (code) {
                case 1598968902:
                    reply.writeString(descriptor);
                    return true;
            }
            switch (code) {
                case 1:
                    arrayOfInt = data.createIntArray();
                    data.enforceNoDataAvail();
                    setFrameColors(arrayOfInt);
                    reply.writeNoException();
                    return true;
                case 2:
                    openSession();
                    reply.writeNoException();
                    return true;
                case 3:
                    closeSession();
                    reply.writeNoException();
                    return true;
                case 4:
                    _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    _result = register(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 5:
                    _arg0 = data.readString();
                    _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    bool1 = registerSDK(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(bool1);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IGlyphService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.nothing.thirdparty.IGlyphService";
            }

            public void setFrameColors(int[] colors) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                    _data.writeIntArray(colors);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void openSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean register(String key) throws RemoteException {
                boolean _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                    _data.writeString(key);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readBoolean();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public boolean registerSDK(String key, String device) throws RemoteException {
                boolean _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                    _data.writeString(key);
                    _data.writeString(device);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readBoolean();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
    }

    static class Proxy implements IGlyphService {
        private IBinder mRemote;

        Proxy(IBinder remote) {
            this.mRemote = remote;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        public String getInterfaceDescriptor() {
            return "com.nothing.thirdparty.IGlyphService";
        }

        public void setFrameColors(int[] colors) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                _data.writeIntArray(colors);
                boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        public void openSession() throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        public void closeSession() throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        public boolean register(String key) throws RemoteException {
            boolean _result;
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                _data.writeString(key);
                boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readBoolean();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        public boolean registerSDK(String key, String device) throws RemoteException {
            boolean _result;
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken("com.nothing.thirdparty.IGlyphService");
                _data.writeString(key);
                _data.writeString(device);
                boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readBoolean();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }
    }
}
